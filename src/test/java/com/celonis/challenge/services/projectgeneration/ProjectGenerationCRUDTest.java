package com.celonis.challenge.services.projectgeneration;

import com.celonis.challenge.controllers.projectgenerationtask.TaskController;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectGenerationCRUDTest {

    private static final String UUID_PATTERN = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    TaskController taskController;

    @Test
    public void createNew_List_Get() {
        //given
        var taskName = "testTaskName";
        var task = new ProjectGenerationTask();
        task.setName(taskName);
        task.setCreationDate(getYesterday());

        //when
        var createdTask = taskController.createTask(task);

        //then
        assertNotNull(repository.findById(createdTask.getId()).get());
        assertEquals(taskName, createdTask.getName());
        assertTrue(Pattern.matches(UUID_PATTERN, createdTask.getId()));
        assertEquals(dateToString(new Date()), dateToString(createdTask.getCreationDate()));
        assertNull(createdTask.getStorageLocation());

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

    private ProjectGenerationTask createTask() {
        var task = new ProjectGenerationTask();
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

    private boolean taskListHasTask(List<ProjectGenerationTask> tasks, String taskId) {
        for (var task : tasks) {
            if (task.getId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }

}
