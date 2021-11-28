package com.celonis.challenge.services.countertask.execution;

import com.celonis.challenge.model.countertask.entity.CounterTaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class CounterTaskExecutionServiceImpl implements CounterTaskExecutionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ConcurrentHashMap<String, CounterTaskExecutionState> stateMap = new ConcurrentHashMap<>();

    private final CounterTaskSynchronizer synchronizer;

    public CounterTaskExecutionServiceImpl(CounterTaskSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Async
    @Override
    public void executeTask(CounterTaskExecutionState task) {
        var existingTask = stateMap.putIfAbsent(task.getId(), task);
        if (existingTask == null) {
            var runningTask = stateMap.get(task.getId());
            while(runningTask.isRunning()) {
                if(runningTask.getX().get() >= runningTask.getY() &&
                        runningTask.getStatus().compareAndSet(CounterTaskStatus.RUNNING, CounterTaskStatus.FINISHED)) {
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
        if(runningTask != null && runningTask.getStatus().compareAndSet(CounterTaskStatus.RUNNING, CounterTaskStatus.STOPPED)) {
            updateTask(runningTask);
        }
    }

    private void updateTask(CounterTaskExecutionState task) {
        synchronizer.updateTask(task);
    }

    private void sleep(String taskId) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            logger.error("CounterTask was interrupted: " + taskId);
            e.printStackTrace();
        }
    }
}
