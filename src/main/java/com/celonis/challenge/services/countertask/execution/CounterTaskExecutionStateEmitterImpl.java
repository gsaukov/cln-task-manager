package com.celonis.challenge.services.countertask.execution;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class CounterTaskExecutionStateEmitterImpl implements CounterTaskExecutionStateEmitter {

    private final CounterTaskExecutionService executionService;

    public CounterTaskExecutionStateEmitterImpl(CounterTaskExecutionService executionService) {
        this.executionService = executionService;
    }

    @Async
    @Override
    public void subscribeToExecutionEvents(SseEmitter emitter, String taskId) {
        try {
            CounterTaskExecutionState executionState = executionService.getTaskState(taskId);
            while (executionState!=null && executionState.isRunning()) {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .id(Long.toString(System.currentTimeMillis()))
                        .data(executionState);
                emitter.send(event);
                Thread.sleep(1000l);
                executionState = executionService.getTaskState(taskId);
            }
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .id(Long.toString(System.currentTimeMillis()))
                    .data("No Task or Task is Not running");
            emitter.send(event);
            emitter.complete();
        } catch (Exception ex) {
            emitter.completeWithError(ex);
        }
    }

}
