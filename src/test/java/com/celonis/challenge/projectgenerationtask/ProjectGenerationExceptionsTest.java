package com.celonis.challenge.projectgenerationtask;

import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskController;
import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectGenerationExceptionsTest {

    private static final UUID DO_NOT_EXIST = UUID.randomUUID();

    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    ProjectGenerationTaskController taskController;

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

    private ProjectGenerationTaskModel createTask() {
        var task = new ProjectGenerationTaskModel();
        task.setName("testTaskName");
        return taskController.createTask(task);
    }

}
