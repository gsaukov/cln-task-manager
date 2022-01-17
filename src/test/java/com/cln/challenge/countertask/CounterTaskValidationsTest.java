package com.cln.challenge.countertask;

import com.cln.challenge.controllers.countertask.CounterTaskController;
import com.cln.challenge.controllers.countertask.CounterTaskModel;
import com.cln.challenge.services.countertask.CounterTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CounterTaskController.class)
public class CounterTaskValidationsTest {

    private final String HEADER_NAME = "Cln-Auth";
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

    @Test
    public void unparsableUUID() throws Exception {
        //given
        var expectedResponse = "{\"status\":\"BAD_REQUEST\",\"messages\":[\"Invalid UUID string: badUUID\"]}";
        var badUUID = "badUUID";
        //when
        var result = mockMvc.perform(get("/api/v1/countertasks/{badUUID}", badUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_NAME, HEADER_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

}
