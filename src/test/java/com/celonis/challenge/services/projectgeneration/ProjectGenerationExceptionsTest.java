package com.celonis.challenge.services.projectgeneration;

import com.celonis.challenge.controllers.projectgenerationtask.TaskController;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectGenerationExceptionsTest {

    private static final String DO_NOT_EXIST = "DO_NOT_EXIST";

    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    TaskController taskController;

    @Test
    public void getTaskNotExists() {
        //when
        assertThrows(NoSuchElementException.class, () -> taskController.getTask(DO_NOT_EXIST));
    }

    @Test
    public void updateTaskNotExists() {
        //when
        assertThrows(NoSuchElementException.class, () -> taskController.updateTask(DO_NOT_EXIST, createTask()));
    }

    @Test
    public void executeTaskNotExists() {
        //when
        assertThrows(NoSuchElementException.class, () -> taskController.executeTask(DO_NOT_EXIST));
    }

    @Test
    public void getTaskResultsInIllegalState() {
        //given
        var task = createTask();
        //when
        assertThrows(IllegalStateException.class, () -> taskController.getResult(task.getId()));
    }

    private ProjectGenerationTask createTask() {
        var task = new ProjectGenerationTask();
        task.setName("testTaskName");
        return taskController.createTask(task);
    }

}
