package com.celonis.challenge.services.countertask.execution;

import com.celonis.challenge.model.countertask.entity.CounterTaskStatus;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CounterTaskExecutionState {

    private final String id;

    private final AtomicInteger x;

    private final Integer y;

    private AtomicReference<CounterTaskStatus> status;

    public CounterTaskExecutionState(String id, Integer x, Integer y) {
        this.id = id;
        this.x = new AtomicInteger(x);
        this.y = y;
        this.status = new AtomicReference<>(CounterTaskStatus.RUNNING);
    }

    public String getId() {
        return id;
    }

    public AtomicInteger getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public boolean isRunning() {
        return status.get().equals(CounterTaskStatus.RUNNING);
    }

    public AtomicReference<CounterTaskStatus> getStatus() {
        return status;
    }


}