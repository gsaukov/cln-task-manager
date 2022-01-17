package com.cln.challenge.maintenance;

import com.cln.challenge.model.countertask.entity.CounterTask;
import com.cln.challenge.model.countertask.repository.CounterTaskRepository;
import com.cln.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.cln.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = { "clnTaskManager.taskCleanup.enabled=true" })
public class ScheduledCleanupTest {

    private static String TASK_NAME = "TASK_NAME";

    @Autowired
    EntityManager entityManager;

    @Autowired
    CounterTaskRepository counterTaskRepository;

    @Autowired
    ProjectGenerationTaskRepository projectGenerationTaskRepository;

    @Test
    public void createNewForCleaning() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        //given 2 task for each type older than 10 days and 1 task younger.
        var youngCounterTaskId = createCounterTaskAndPersist(9).getId();
        createCounterTaskAndPersist(11);
        createCounterTaskAndPersist(12);

        var youngProjectGenerationTaskId = createProjectGenerationTaskAndPersist(6).getId();
        createProjectGenerationTaskAndPersist(11);
        createProjectGenerationTaskAndPersist(14);

        //when cleanup job executes every second it will remove old tasks.
        Thread.sleep(1500l);

        //then all Old task were removed and only young remains
        var counterTasks = counterTaskRepository.findAll();
        assertEquals(1, counterTasks.size());
        assertTrue(counterTasks.get(0).getId().equals(youngCounterTaskId));

        var projectGenerationTasks = projectGenerationTaskRepository.findAll();
        assertEquals(1, projectGenerationTasks.size());
        assertTrue(projectGenerationTasks.get(0).getId().equals(youngProjectGenerationTaskId));
    }

    private CounterTask createCounterTaskAndPersist(int daysAgo) throws NoSuchFieldException, IllegalAccessException {
        var date = LocalDateTime.now().minusDays(daysAgo);
        var task = new CounterTask(TASK_NAME, 0, 100);
        updatePrivateCreatedAt(task, date);
        return counterTaskRepository.save(task);
    }

    private ProjectGenerationTask createProjectGenerationTaskAndPersist(int daysAgo) {
        Date dateBefore = Date.from(Instant.now().minus(Duration.ofDays(daysAgo)));
        var task = new ProjectGenerationTask();
        task.setName(TASK_NAME);
        task.setCreationDate(dateBefore);
        return projectGenerationTaskRepository.save(task);
    }

    //Reflection, but there is no other way to fake the date, Mocks/spys are rejected by repository instanceof.
    private void updatePrivateCreatedAt(CounterTask task, LocalDateTime localDateTime) throws NoSuchFieldException, IllegalAccessException {
        Field field = task.getClass().getSuperclass().getDeclaredField("createdAt");
        field.setAccessible(true);
        field.set(task, localDateTime);
    }

}
