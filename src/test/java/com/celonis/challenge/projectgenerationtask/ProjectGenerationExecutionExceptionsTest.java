package com.celonis.challenge.projectgenerationtask;

import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskController;
import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.celonis.challenge.exceptions.TaskExecutionException;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import com.celonis.challenge.services.projectgenerationtask.ProjectGenerationFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProjectGenerationExecutionExceptionsTest {

    @MockBean
    ProjectGenerationFileService fileService;

    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    ProjectGenerationTaskController taskController;

    @Test
    public void taskExecutionException() {
        //given
        var task = createTask();
        when(fileService.createAndStoreTaskFile(any(String.class))).thenThrow(new TaskExecutionException("Task File creation failed"));
        //when
        assertThrows(TaskExecutionException.class, () -> taskController.executeTask(task.getId()));
    }

    private ProjectGenerationTaskModel createTask() {
        var task = new ProjectGenerationTaskModel();
        task.setName("testTaskName");
        return taskController.createTask(task);
    }

}
