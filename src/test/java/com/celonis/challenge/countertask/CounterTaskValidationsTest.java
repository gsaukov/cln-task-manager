package com.celonis.challenge.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskController;
import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.services.countertask.CounterTaskService;
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

@WebMvcTest(controllers = CounterTaskController.class)
public class CounterTaskValidationsTest {

    private final String HEADER_NAME = "Celonis-Auth";
    private final String HEADER_VALUE = "totally_secret";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CounterTaskService taskService;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void invalidXgreaterY() throws Exception {
        //given
        var expectedResponse = "{\"status\":\"BAD_REQUEST\",\"messages\":[\"Field: [x] Y must be greater X\"]}";
        var task = new CounterTaskModel();
        task.setName("1234");
        task.setX(100);
        task.setY(10);
        //when
        var result = mockMvc.perform(post("/api/v1/countertasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_NAME, HEADER_VALUE)
                        .content(mapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

}
