package com.cln.challenge.countertask;

import com.cln.challenge.controllers.countertask.CounterTaskController;
import com.cln.challenge.controllers.countertask.CounterTaskModel;
import com.cln.challenge.model.countertask.entity.CounterTaskStatus;
import com.cln.challenge.model.countertask.repository.CounterTaskRepository;
import com.cln.challenge.services.countertask.execution.CounterTaskExecutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        assertThrows(IllegalStateException.class, () -> taskController.executeTask(task.getId()));

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

    @Test
    public void executeSingleWithMultipleThreadsAsync() throws InterruptedException {
        //given
        var concurrentThreads = 20;
        var task = createTask(1, 100);
        var lockCount = new AtomicInteger(0);
        var illegalStateCount = new AtomicInteger(0);

        //when
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < concurrentThreads; i++) {
            executorService.execute(() -> {
                try{
                    taskController.executeTask(task.getId());
                } catch (OptimisticLockingFailureException e) {
                    lockCount.incrementAndGet();
                } catch (IllegalStateException e) {
                    illegalStateCount.incrementAndGet();
                }
            });
        }
        executorService.awaitTermination(1000l, TimeUnit.MILLISECONDS);

        //then only one thread has started and executed task
        assertEquals(lockCount.get() + illegalStateCount.get(), concurrentThreads - 1);

        var finishedTask = taskController.getTask(task.getId());
        assertNotNull(finishedTask);
        assertEquals(CounterTaskStatus.FINISHED.toString(), finishedTask.getStatus());
        assertEquals(finishedTask.getX(), finishedTask.getY());
    }

    private CounterTaskModel createTask(int x, int y) {
        var task = new CounterTaskModel();
        task.setName("testTaskName");
        task.setX(x);
        task.setY(y);
        return taskController.createTask(task);
    }

}
