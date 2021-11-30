package com.celonis.challenge.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskController;
import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.model.countertask.entity.CounterTaskStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@SpringBootTest
@AutoConfigureMockMvc
public class CounterTaskSSETest {

    private final String HEADER_NAME = "Celonis-Auth";
    private final String HEADER_VALUE = "totally_secret";

    private final String SSE_DATA = "data:";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CounterTaskController taskController;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createNew_Run_ConsumeSSE_Finish() throws Exception {

        //given
        var task = createTask(0, 100);
        taskController.executeTask(task.getId());
        Thread.sleep(10l);

        //when
        var result = mockMvc.perform(get("/api/v1/countertasks/{taskId}/taskstate", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_NAME, HEADER_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        //then there were more than 50 events in RUNNING and FINISHED states
        Thread.sleep(1000l);

        var events = getSSEEventData(result);
        assertTrue(events.size() > 50);
        var runningStatesCount = getStatusCount(events, CounterTaskStatus.RUNNING);
        assertTrue(runningStatesCount > 0);
        var finishedStatesCount = getStatusCount(events, CounterTaskStatus.FINISHED);
        assertTrue(finishedStatesCount > 0);
    }

    private List<EventData> getSSEEventData(MvcResult result) throws Exception {
        return result.getResponse().getContentAsString().lines()
                .filter(l -> l.startsWith(SSE_DATA))
                .map(l -> l.replace(SSE_DATA, "" ))
                .map(l -> {
                    try {
                        return mapper.readValue(l, EventData.class);
                    } catch (JsonProcessingException e) {
                        throw new IllegalArgumentException("json processing failed");
                    }
                })
                .collect(Collectors.toList());
    }

    private long getStatusCount(List<EventData> events, CounterTaskStatus status) {
        return events.stream().filter(d -> d.getStatus().equals(status.toString())).count();
    }

    private CounterTaskModel createTask(int x, int y) {
        var task = new CounterTaskModel();
        task.setName("testTaskName");
        task.setX(x);
        task.setY(y);
        return taskController.createTask(task);
    }

    private static class EventData {

        private String id;
        private int x;
        private int y;
        private String status;
        private boolean running;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }
}
