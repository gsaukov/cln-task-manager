package com.celonis.challenge.services.countertask;

import com.celonis.challenge.model.countertask.entity.CounterTask;

import java.util.List;

public interface CounterTaskExecutionService {

    void executeTask(String taskId);

    void stopTask(String taskId);

}
