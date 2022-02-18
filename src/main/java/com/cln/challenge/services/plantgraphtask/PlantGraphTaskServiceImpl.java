package com.cln.challenge.services.plantgraphtask;

import com.cln.challenge.controllers.plantgraphtask.PlantGraphTaskModel;
import com.cln.challenge.model.plantgraphtask.entity.PlantGraphTask;
import com.cln.challenge.model.plantgraphtask.repository.PlantGraphTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class PlantGraphTaskServiceImpl implements PlantGraphTaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PlantGraphTaskRepository repository;

    public PlantGraphTaskServiceImpl(PlantGraphTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlantGraphTask getTask(UUID taskId) {
        return repository.findById(taskId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<PlantGraphTask> listTasks() {
        return repository.findAll();
    }

    @Override
    public PlantGraphTask createTask(PlantGraphTaskModel model) {
        return repository.save(toTask(model));
    }

    @Override
    public void delete(UUID taskId) {
        PlantGraphTask task = getTask(taskId);
        repository.deleteById(task.getId());
    }

    @Override
    public void executeTask(UUID taskId) {
        PlantGraphTask task = getTask(taskId);
    }

    @Override
    public void stopTask(UUID taskId) {
    }

    private PlantGraphTask toTask(PlantGraphTaskModel model) {
        return new PlantGraphTask();
    }

}
