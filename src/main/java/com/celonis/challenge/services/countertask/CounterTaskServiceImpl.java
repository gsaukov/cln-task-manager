package com.celonis.challenge.services.countertask;

import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CounterTaskServiceImpl implements CounterTaskService{

    private final CounterTaskRepository repository;

    public CounterTaskServiceImpl(CounterTaskRepository repository) {
        this.repository = repository;
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
    public CounterTask createTask(CounterTask ctTask) {
        return repository.save(ctTask);
    }

    @Override
    public CounterTask update(String taskId, CounterTask updateCtTask) {
        CounterTask existingCtTask = getTask(taskId);
        existingCtTask.setCounterTaskName(updateCtTask.getCounterTaskName());
        return existingCtTask;
    }

    @Override
    public void delete(String taskId) {
        repository.deleteById(getTask(taskId).getId());
    }
}
