package com.cln.challenge.projectgenerationtask;

import com.cln.challenge.controllers.projectgenerationtask.ProjectGenerationTaskController;
import com.cln.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.cln.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectGenerationCRUDTest {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    ProjectGenerationTaskController taskController;

    @Test
    public void createNew_List_Get() {
        //given
        var taskName = "testTaskName";
        var task = new ProjectGenerationTaskModel();
        task.setName(taskName);
        task.setCreationDate(getYesterday());

        //when
        var createdTask = taskController.createTask(task);

        //then
        var persistedTask = repository.findById(createdTask.getId()).get();
        assertNotNull(persistedTask);
        assertEquals(taskName, createdTask.getName());
        assertEquals(dateToString(new Date()), dateToString(createdTask.getCreationDate()));
        assertNull(persistedTask.getStorageLocation());

        var taskList = taskController.listTasks();
        assertFalse(taskList.isEmpty());
        assertTrue(taskListHasTask(taskList, createdTask.getId()));

        var getTask = taskController.getTask(createdTask.getId());
        assertNotNull(getTask);
        assertEquals(taskName, getTask.getName());
    }

    @Test
    public void createNew_Update_Delete() {
        //given
        var task = createTask();
        var newTaskName = "newTestTaskName";

        //when
        task.setName(newTaskName);
        taskController.updateTask(task.getId(), task);
        var updatedTask = taskController.getTask(task.getId());

        //then
        assertEquals(updatedTask.getName(), newTaskName);

        //when
        taskController.deleteTask(task.getId());

        //then
        assertTrue(repository.findById(updatedTask.getId()).isEmpty());
    }

    private ProjectGenerationTaskModel createTask() {
        var task = new ProjectGenerationTaskModel();
        task.setName("testTaskName");
        return taskController.createTask(task);
    }

    private Date getYesterday() {
        var yesterday = Instant.now().minus(Duration.ofDays(1));
        return Date.from(yesterday);
    }

    private String dateToString(Date date) {
        var formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.format(date);
    }

    private boolean taskListHasTask(List<ProjectGenerationTaskModel> tasks, UUID taskId) {
        for (var task : tasks) {
            if (task.getId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }

}
