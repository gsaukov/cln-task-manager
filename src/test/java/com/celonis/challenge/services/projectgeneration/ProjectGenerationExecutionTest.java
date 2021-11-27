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
public class ProjectGenerationExecutionTest {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String CONTENT_HEADER_NAME = "name=\"attachment\"";
    private static final String CONTENT_HEADER_FILENAME = "filename=\"challenge.zip\"";


    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    TaskController taskController;

    @Test
    public void executeNew_GetResult() {
        //given
        var task = createTask();

        //when
        taskController.executeTask(task.getId());
        var result = taskController.getResult(task.getId());
        var executedTask = repository.findById(task.getId()).get();

        //then
        var body = result.getBody();
        var header = result.getHeaders().get(CONTENT_DISPOSITION);
        assertNotNull(header);
        assertTrue(headersHasValue(header, CONTENT_HEADER_NAME));
        assertTrue(headersHasValue(header, CONTENT_HEADER_FILENAME));
        assertTrue(body.getFile().exists());

        assertNotNull(executedTask.getStorageLocation());
    }

    private ProjectGenerationTask createTask() {
        var task = new ProjectGenerationTask();
        task.setName("testTaskName");
        return taskController.createTask(task);
    }

    private boolean headersHasValue(List<String> headers, String value) {
        for (var header : headers) {
            if (header.contains(value)) {
                return true;
            }
        }
        return false;
    }

}
