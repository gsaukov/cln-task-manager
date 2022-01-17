package com.cln.challenge.services.countertask.execution;

public interface CounterTaskExecutionStateSynchronizer {

    void synchronizeWithDB(CounterTaskExecutionState task);

}
