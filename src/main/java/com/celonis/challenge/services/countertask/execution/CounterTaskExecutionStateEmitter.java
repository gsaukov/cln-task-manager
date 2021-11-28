package com.celonis.challenge.services.countertask.execution;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface CounterTaskExecutionStateEmitter {

    void subscribeToExecutionEvents(SseEmitter emitter, String taskId);

}
