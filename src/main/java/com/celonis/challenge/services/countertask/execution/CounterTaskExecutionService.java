package com.celonis.challenge.services.countertask.execution;

public interface CounterTaskExecutionService {

    void executeTask(CounterTaskExecutionState task);

    void stopTask(String taskId);

    void deleteTask(String taskId);

    CounterTaskExecutionState getTaskState(String taskId);

}
