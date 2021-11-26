package com.celonis.challenge.services.projectgeneration;

import com.celonis.challenge.controllers.projectgenerationtask.TaskController;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ProjectGenerationTest {

    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    TaskController taskController;

    @Autowired
    TaskService taskService;

    @Test
    public void createNewTaskTest () throws IOException {
        ProjectGenerationTask task = createTask();
        taskController.createTask(task);
        repository.findById(task.getId());
    }

    private ProjectGenerationTask createTask() {
        ProjectGenerationTask task = new ProjectGenerationTask();
        task.setName("test");
        return task;
    }


}
