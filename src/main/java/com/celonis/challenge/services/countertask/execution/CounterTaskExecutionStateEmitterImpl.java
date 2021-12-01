package com.celonis.challenge.services.countertask.execution;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class CounterTaskExecutionStateEmitterImpl implements CounterTaskExecutionStateEmitter {

    @Value("${clnTaskManager.counterTask.emitterStepMs}")
    private Long emitterStepMs;

    private final CounterTaskExecutionService executionService;

    public CounterTaskExecutionStateEmitterImpl(CounterTaskExecutionService executionService) {
        this.executionService = executionService;
    }

    @Async
    @Override
    public void subscribeToExecutionEvents(SseEmitter emitter, String taskId) {
        try {
            CounterTaskExecutionState executionState = executionService.getTaskState(taskId);
            while (executionState != null) {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .id(Long.toString(System.currentTimeMillis()))
                        .data(executionState);
                emitter.send(event);
                Thread.sleep(emitterStepMs);
                executionState = executionService.getTaskState(taskId);
            }
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .id(Long.toString(System.currentTimeMillis()))
                    .data("No Task with " + taskId + " running");
            emitter.send(event);
            emitter.complete();
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }

}
