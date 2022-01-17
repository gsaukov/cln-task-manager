package com.cln.challenge.services.countertask.execution;

import com.cln.challenge.model.countertask.entity.CounterTaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CounterTaskExecutionServiceImpl implements CounterTaskExecutionService {

    @Value("${clnTaskManager.counterTask.timeoutMs}")
    private Long timeoutMs;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ConcurrentHashMap<UUID, CounterTaskExecutionState> stateMap = new ConcurrentHashMap<>();

    private final CounterTaskExecutionStateSynchronizer synchronizer;

    public CounterTaskExecutionServiceImpl(CounterTaskExecutionStateSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Async
    @Override
    public void executeTask(CounterTaskExecutionState task) {
        CounterTaskExecutionState existingTask = stateMap.putIfAbsent(task.getId(), task);
        if (existingTask == null) {
            CounterTaskExecutionState runningTask = stateMap.get(task.getId());
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
    public void stopTask(UUID taskId) {
        CounterTaskExecutionState runningTask = stateMap.get(taskId);
        //you can only stop RUNNING task and then update db accordingly.
        if(runningTask != null && runningTask.getStatus().compareAndSet(CounterTaskStatus.RUNNING, CounterTaskStatus.STOPPED)) {
            updateTask(runningTask);
        }
    }

    @Override
    public void deleteTask(UUID taskId) {
        //just set status to stopped.
        CounterTaskExecutionState runningTask = stateMap.get(taskId);
        if(runningTask != null) {
            runningTask.getStatus().set(CounterTaskStatus.STOPPED);
        }
    }

    @Override
    public CounterTaskExecutionState getTaskState(UUID taskId) {
        //i dont like to return mutable object from here but for simplicity let it be.
        return stateMap.get(taskId);
    }

    private void updateTask(CounterTaskExecutionState task) {
        synchronizer.synchronizeWithDB(task);
    }

    private void sleep(UUID taskId) {
        try {
            Thread.sleep(timeoutMs);
        } catch (InterruptedException e) {
            logger.error("CounterTask was interrupted: " + taskId);
            e.printStackTrace();
        }
    }
}
