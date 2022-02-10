package com.cln.challenge.services.graphtask;

import com.cln.challenge.controllers.graphtask.GraphTaskModel;
import com.cln.challenge.model.graphtask.entity.GraphTask;
import com.cln.challenge.model.graphtask.repository.GraphTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class GraphTaskServiceImpl implements GraphTaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GraphTaskRepository repository;

    public GraphTaskServiceImpl(GraphTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public GraphTask getTask(UUID taskId) {
        return repository.findById(taskId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<GraphTask> listTasks() {
        return repository.findAll();
    }

    @Override
    public GraphTask createTask(GraphTaskModel model) {
        return repository.save(toTask(model));
    }

    @Override
    public void delete(UUID taskId) {
        GraphTask task = getTask(taskId);
        repository.deleteById(task.getId());
    }

    @Override
    public void executeTask(UUID taskId) {
        GraphTask task = getTask(taskId);
    }

    @Override
    public void stopTask(UUID taskId) {
    }

    private GraphTask toTask(GraphTaskModel graphTaskModel) {
        return new GraphTask();
    }

}
