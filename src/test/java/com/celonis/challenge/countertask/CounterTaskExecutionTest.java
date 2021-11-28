package com.celonis.challenge.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskController;
import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.model.countertask.entity.CounterTaskStatus;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import com.celonis.challenge.services.countertask.CounterTaskService;
import com.celonis.challenge.services.countertask.execution.CounterTaskExecutionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//It is async test so it might fail under thread starvation conditions or other issues with concurrency
// here you can make execution faster: clnTaskManager.counterTask.timeoutMs
public class CounterTaskExecutionTest {

    @Autowired
    CounterTaskRepository repository;

    @Autowired
    CounterTaskController taskController;

    @Autowired
    CounterTaskExecutionService executionService;

    @Test
    public void createNew_Run_Finish() throws InterruptedException {
        //given
        var task = createTask(100, 200);

        //when
        taskController.executeTask(task.getId());
        Thread.sleep(100l);

        //then check repository it must be running
        var persistedTask = repository.findById(task.getId()).get();
        assertNotNull(persistedTask);
        assertEquals(CounterTaskStatus.RUNNING, persistedTask.getStatus());

        //then check controller it must be running
        var runningTask = taskController.getTask(task.getId());
        assertNotNull(runningTask);
        assertEquals(CounterTaskStatus.RUNNING.toString(), runningTask.getStatus());

        Thread.sleep(1000l);

        //then check repository it must be finished
        persistedTask = repository.findById(task.getId()).get();
        assertNotNull(persistedTask);
        assertEquals(CounterTaskStatus.FINISHED, persistedTask.getStatus());

        //then check controller it must be finished
        runningTask = taskController.getTask(task.getId());
        assertNotNull(runningTask);
        assertEquals(CounterTaskStatus.FINISHED.toString(), runningTask.getStatus());
        assertEquals(runningTask.getX(), runningTask.getY());
    }

    @Test
    public void createNew_Run_Stop_Run() throws InterruptedException {
        //given
        var task = createTask(100, 200);

        //when
        taskController.executeTask(task.getId());
        Thread.sleep(10l);
        taskController.stopTask(task.getId());
        Thread.sleep(10l);

        //then check controller it must be stopped
        var stoppedTask = taskController.getTask(task.getId());
        var stoppedX = stoppedTask.getX();
        assertNotNull(stoppedTask);
        assertEquals(CounterTaskStatus.STOPPED.toString(), stoppedTask.getStatus());
        assertTrue(stoppedTask.getX() < stoppedTask.getY());

        //when rerun
        taskController.executeTask(task.getId());
        Thread.sleep(10l);

        //then check controller it must be still stopped X does not change
        var reranTask = taskController.getTask(task.getId());
        assertNotNull(reranTask);
        assertEquals(CounterTaskStatus.STOPPED.toString(), reranTask.getStatus());
        assertEquals(stoppedX, reranTask.getX());
    }

    @Test
    public void createNew_Run_Delete() throws InterruptedException {
        //given
        var task = createTask(100, 200);

        //when
        taskController.executeTask(task.getId());
        Thread.sleep(20l);
        taskController.deleteTask(task.getId());
        Thread.sleep(20l);

        //then check controller there is no task
        var deletedTask = repository.findById(task.getId());
        assertTrue(deletedTask.isEmpty());

        //then check executionService task is there in stopped state, for consumers.
        var stoppedTask = executionService.getTaskState(task.getId());
        assertEquals(CounterTaskStatus.STOPPED, stoppedTask.getStatus().get());
    }

    private CounterTaskModel createTask(int x, int y) {
        var task = new CounterTaskModel();
        task.setName("testTaskName");
        task.setX(x);
        task.setY(y);
        return taskController.createTask(task);
    }

}
