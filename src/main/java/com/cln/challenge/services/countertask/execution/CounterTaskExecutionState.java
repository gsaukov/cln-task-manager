package com.cln.challenge.services.countertask.execution;

import com.cln.challenge.model.countertask.entity.CounterTask;
import com.cln.challenge.model.countertask.entity.CounterTaskStatus;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CounterTaskExecutionState {

    private final UUID id;

    private final AtomicInteger x;

    private final Integer y;

    private AtomicReference<CounterTaskStatus> status;

    public static CounterTaskExecutionState createRunningTask(CounterTask task) {
        return new CounterTaskExecutionState(task.getId(), task.getX(), task.getY(), CounterTaskStatus.RUNNING);
    }

    private CounterTaskExecutionState(UUID id, Integer x, Integer y, CounterTaskStatus status) {
        this.id = id;
        this.x = new AtomicInteger(x);
        this.y = y;
        this.status = new AtomicReference<>(status);
    }

    public UUID getId() {
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
