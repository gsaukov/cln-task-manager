package com.celonis.challenge.services.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import com.celonis.challenge.services.countertask.execution.CounterTaskExecutionService;
import com.celonis.challenge.services.countertask.execution.CounterTaskExecutionState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CounterTaskServiceImpl implements CounterTaskService{

    private final CounterTaskRepository repository;

    private final CounterTaskExecutionService executionService;

    public CounterTaskServiceImpl(CounterTaskRepository repository, CounterTaskExecutionService executionService) {
        this.repository = repository;
        this.executionService = executionService;
    }

    @Override
    public CounterTask getTask(String taskId) {
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
    public void delete(String taskId) {
        CounterTask task = getTask(taskId);
        executionService.stopTask(task.getId());
        repository.deleteById(task.getId());
    }

    @Override
    public void executeTask(String taskId) {
        executionService.executeTask(toExecutionState(getTask(taskId)));
    }

    @Override
    public void stopTask(String taskId) {
        executionService.stopTask(getTask(taskId).getId());
    }

    private CounterTask toTask(CounterTaskModel model) {
        return new CounterTask(model.getName(), model.getX(), model.getY());
    }

    private CounterTaskExecutionState toExecutionState(CounterTask task) {
        return new CounterTaskExecutionState(task.getId(), task.getX(), task.getY());
    }
}
