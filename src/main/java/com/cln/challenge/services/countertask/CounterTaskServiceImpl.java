package com.cln.challenge.services.countertask;

import com.cln.challenge.controllers.countertask.CounterTaskModel;
import com.cln.challenge.model.countertask.entity.CounterTask;
import com.cln.challenge.model.countertask.entity.CounterTaskStatus;
import com.cln.challenge.model.countertask.repository.CounterTaskRepository;
import com.cln.challenge.services.countertask.execution.CounterTaskExecutionService;
import com.cln.challenge.services.countertask.execution.CounterTaskExecutionState;
import com.cln.challenge.services.countertask.execution.CounterTaskExecutionStateEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class CounterTaskServiceImpl implements CounterTaskService {

    @Value("${clnTaskManager.counterTask.emitterDurationMs}")
    private Long emitterDurationMs;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CounterTaskRepository repository;

    private final CounterTaskExecutionService executionService;

    private final CounterTaskExecutionStateEmitter executionStateEmitter;

    public CounterTaskServiceImpl(CounterTaskRepository repository, CounterTaskExecutionService executionService,
                                  CounterTaskExecutionStateEmitter executionStateEmitter) {
        this.repository = repository;
        this.executionService = executionService;
        this.executionStateEmitter = executionStateEmitter;
    }

    @Override
    public CounterTask getTask(UUID taskId) {
        return repository.findById(taskId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<CounterTask> listTasks() {
        return repository.findAll();
    }

    @Override
    public CounterTask createTask(CounterTaskModel model) {
        return repository.save(toTask(model));
    }

    @Override
    public void delete(UUID taskId) {
        CounterTask task = getTask(taskId);
        executionService.deleteTask(task.getId());
        repository.deleteById(task.getId());
    }

    @Override
    public void executeTask(UUID taskId) {
        CounterTask task = getTask(taskId);
        if (task.getStatus().equals(CounterTaskStatus.ACTIVE)) {
            task.setStatus(CounterTaskStatus.RUNNING);
            repository.saveAndFlush(task);
            executionService.executeTask(CounterTaskExecutionState.createRunningTask(task));
        } else {
            throw new IllegalStateException("Task is " + taskId + " not in ACTIVE state");
        }
    }

    @Override
    public void stopTask(UUID taskId) {
        executionService.stopTask(getTask(taskId).getId());
    }

    @Override
    public SseEmitter subscribeToExecutionEvents(UUID taskId) {
        CounterTask task = getTask(taskId);
        SseEmitter sseEmitter = new SseEmitter(emitterDurationMs);
        sseEmitter.onTimeout(sseEmitter::complete);
        executionStateEmitter.subscribeToExecutionEvents(sseEmitter, task.getId());
        return sseEmitter;
    }

    private CounterTask toTask(CounterTaskModel model) {
        return new CounterTask(model.getName(), model.getX(), model.getY());
    }

}
