package com.cln.challenge.projectgenerationtask;

import com.cln.challenge.controllers.projectgenerationtask.ProjectGenerationTaskController;
import com.cln.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.cln.challenge.exceptions.TaskExecutionException;
import com.cln.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import com.cln.challenge.services.projectgenerationtask.ProjectGenerationFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

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
        when(fileService.createAndStoreTaskFile(any(UUID.class))).thenThrow(new TaskExecutionException("Task File creation failed"));
        //when
        assertThrows(TaskExecutionException.class, () -> taskController.executeTask(task.getId()));
    }

    private ProjectGenerationTaskModel createTask() {
        var task = new ProjectGenerationTaskModel();
        task.setName("testTaskName");
        return taskController.createTask(task);
    }

}
