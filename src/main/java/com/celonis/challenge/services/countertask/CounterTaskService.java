package com.celonis.challenge.services.countertask;

import com.celonis.challenge.model.countertask.entity.CounterTask;

import java.util.List;

public interface CounterTaskService {

    CounterTask getTask(String taskId);

    List<CounterTask> listTasks();

    CounterTask createTask(CounterTask ctTask);

    CounterTask update(String taskId, CounterTask ctTask);

    void delete(String taskId);

}
