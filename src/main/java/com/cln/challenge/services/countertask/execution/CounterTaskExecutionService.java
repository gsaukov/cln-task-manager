package com.cln.challenge.services.countertask.execution;

import java.util.UUID;

public interface CounterTaskExecutionService {

    void executeTask(CounterTaskExecutionState task);

    void stopTask(UUID taskId);

    void deleteTask(UUID taskId);

    CounterTaskExecutionState getTaskState(UUID taskId);

}
