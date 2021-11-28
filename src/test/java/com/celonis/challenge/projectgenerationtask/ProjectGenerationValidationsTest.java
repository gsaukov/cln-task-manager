package com.celonis.challenge.projectgenerationtask;

import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskController;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.services.projectgenerationtask.ProjectGenerationTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectGenerationTaskController.class)
public class ProjectGenerationValidationsTest {

    private final String HEADER_NAME = "Celonis-Auth";
    private final String HEADER_VALUE = "totally_secret";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectGenerationTaskService taskService;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void invalidTaskName() throws Exception {
        //given
        var expectedResponse = "{\"status\":\"BAD_REQUEST\",\"messages\":[\"Field: [name] size must be between 3 and 128\"]}";
        var task = new ProjectGenerationTask();
        task.setName("12");
        //when
        var result = mockMvc.perform(post("/api/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_NAME, HEADER_VALUE)
                        .content(mapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

}
