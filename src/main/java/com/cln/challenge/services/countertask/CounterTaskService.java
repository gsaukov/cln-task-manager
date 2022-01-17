package com.cln.challenge.services.countertask;

import com.cln.challenge.controllers.countertask.CounterTaskModel;
import com.cln.challenge.model.countertask.entity.CounterTask;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

public interface CounterTaskService {

    CounterTask getTask(UUID taskId);

    List<CounterTask> listTasks();

    CounterTask createTask(CounterTaskModel ctTask);

    void delete(UUID taskId);

    void executeTask(UUID taskId);

    void stopTask(UUID taskId);

    SseEmitter subscribeToExecutionEvents(UUID taskId);

}
