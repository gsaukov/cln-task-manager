package com.celonis.challenge.services.countertask.execution;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

public interface CounterTaskExecutionStateEmitter {

    void subscribeToExecutionEvents(SseEmitter emitter, UUID taskId);

}
