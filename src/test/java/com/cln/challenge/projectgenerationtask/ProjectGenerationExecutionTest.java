package com.cln.challenge.projectgenerationtask;

import com.cln.challenge.controllers.projectgenerationtask.ProjectGenerationTaskController;
import com.cln.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.cln.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectGenerationExecutionTest {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String CONTENT_HEADER_NAME = "name=\"attachment\"";
    private static final String CONTENT_HEADER_FILENAME = "filename=\"challenge.zip\"";


    @Autowired
    ProjectGenerationTaskRepository repository;

    @Autowired
    ProjectGenerationTaskController taskController;

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

    private ProjectGenerationTaskModel createTask() {
        var task = new ProjectGenerationTaskModel();
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
