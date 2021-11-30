package com.celonis.challenge.maintenance;

import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

@SpringBootTest
//        (properties = { "clnTaskManager.taskCleanup.enabled=true" })
public class ScheduledCleanupTest {

    private static final String UPDATE_CREATED_AT = "UPDATE COUNTER_TASK SET X = :d";

    @Autowired
    EntityManager entityManager;

    @Autowired
    CounterTaskRepository counterTaskRepository;

    @Autowired
    ProjectGenerationTaskRepository projectGenerationTaskRepository;

    @Test
    public void createNewForCleaning() throws NoSuchFieldException, IllegalAccessException {
        //given
//        CounterTask task = new CounterTask("testTaskName", 0, 100);
//        updateDate(task, LocalDateTime.now().minusDays(9));
//        counterTaskRepository.save(task);
//
//        //when
//        counterTaskRepository.findAll();
        //then

    }

    private CounterTask createCounterTaskAndPersist() {
        var task = new CounterTask("testTaskName", 0, 100);
        return counterTaskRepository.save(task);
    }

    private void updateDate(CounterTask task, LocalDateTime localDateTime) throws NoSuchFieldException, IllegalAccessException {
        Field f1 = task.getClass().getSuperclass().getDeclaredField("createdAt");
        f1.setAccessible(true);
        f1.set(task, localDateTime);
    }

}
