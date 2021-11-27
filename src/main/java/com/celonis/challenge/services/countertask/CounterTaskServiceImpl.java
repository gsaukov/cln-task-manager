package com.celonis.challenge.services.countertask;

import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
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
    public CounterTask createTask(CounterTaskModel model) {
        return repository.save(toTask(model));
    }

    @Override
    public void delete(String taskId) {
        repository.deleteById(getTask(taskId).getId());
    }

    private CounterTask toTask(CounterTaskModel model) {
        return new CounterTask(model.getName(), model.getX(), model.getY());
    }
}
