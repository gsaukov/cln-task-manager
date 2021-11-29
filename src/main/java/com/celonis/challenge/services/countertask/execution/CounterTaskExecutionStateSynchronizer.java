package com.celonis.challenge.services.countertask.execution;

public interface CounterTaskExecutionStateSynchronizer {

    void synchronizeWithDB(CounterTaskExecutionState task);

}
