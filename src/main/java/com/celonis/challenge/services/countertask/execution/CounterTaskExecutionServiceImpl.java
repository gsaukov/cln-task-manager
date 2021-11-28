package com.celonis.challenge.services.countertask.execution;

import com.celonis.challenge.model.countertask.entity.CounterTaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class CounterTaskExecutionServiceImpl implements CounterTaskExecutionService {

    @Value("${clnTaskManager.counterTask.timeoutMs}")
    private Long timeoutMs;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ConcurrentHashMap<String, CounterTaskExecutionState> stateMap = new ConcurrentHashMap<>();

    private final CounterTaskExecutionStateSynchronizer synchronizer;

    public CounterTaskExecutionServiceImpl(CounterTaskExecutionStateSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Async
    @Override
    public void executeTask(CounterTaskExecutionState task) {
        var existingTask = stateMap.putIfAbsent(task.getId(), task);
        if (existingTask == null) {
            var runningTask = stateMap.get(task.getId());
            updateTask(runningTask); //set state from ACTIVE to RUNNING in db
            while(runningTask.isRunning()) {
                if(runningTask.getX().get() >= runningTask.getY() &&
                        runningTask.getStatus().compareAndSet(CounterTaskStatus.RUNNING, CounterTaskStatus.FINISHED)) {
                    //you can only update it as FINISHED when you moved it from RUNNING state.
                    updateTask(runningTask);
                } else {
                    runningTask.getX().incrementAndGet();
                }
                sleep(runningTask.getId());
            }
        } else {
            logger.info("Task with Id: " + existingTask.getId() + " was already submitted");
        }
    }

    @Override
    public void stopTask(String taskId) {
        var runningTask = stateMap.get(taskId);
        //you can only stop RUNNING task and then update db accordingly.
        if(runningTask != null && runningTask.getStatus().compareAndSet(CounterTaskStatus.RUNNING, CounterTaskStatus.STOPPED)) {
            updateTask(runningTask);
        }
    }

    @Override
    public CounterTaskExecutionState getTaskState(String taskId) {
        //i dont like to return mutable object from here but for simplicity let it be.
        return stateMap.get(taskId);
    }

    private void updateTask(CounterTaskExecutionState task) {
        synchronizer.updateTask(task);
    }

    private void sleep(String taskId) {
        try {
            Thread.sleep(timeoutMs);
        } catch (InterruptedException e) {
            logger.error("CounterTask was interrupted: " + taskId);
            e.printStackTrace();
        }
    }
}
