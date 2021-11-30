package com.celonis.challenge.security;

import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskController;
import com.celonis.challenge.services.projectgenerationtask.ProjectGenerationTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectGenerationTaskController.class)
public class SecurityTest {

    private final String ALLOWED_HEADER = "Allow";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectGenerationTaskService taskService;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void unauthorized() throws Exception {
        //given
        var expectedResponse = "Not authorized";
        //when
        var result = mockMvc.perform(get("/api/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isUnauthorized())
                .andReturn();

        //then
        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    @Test
    public void optionsForUnauthorized() throws Exception {
        //given
        var expectedAllowedMethods = Arrays.asList("POST", "GET", "HEAD", "OPTIONS");
        //when
        var result = mockMvc.perform(options("/api/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andReturn();

        //then

        var actualAllowedMethods = result.getResponse().getHeader(ALLOWED_HEADER);
        for(var method : expectedAllowedMethods) {
            assertTrue(actualAllowedMethods.contains(method));
        }
    }



}
