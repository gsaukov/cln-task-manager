package com.celonis.challenge.services.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.model.countertask.entity.CounterTask;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface CounterTaskService {

    CounterTask getTask(String taskId);

    List<CounterTask> listTasks();

    CounterTask createTask(CounterTaskModel ctTask);

    void delete(String taskId);

    void executeTask(String taskId);

    void stopTask(String taskId);

    SseEmitter subscribeToExecutionEvents(String taskId);

}
