package com.celonis.challenge.services.countertask.execution;

import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounterTaskExecutionStateSynchronizerImpl implements CounterTaskExecutionStateSynchronizer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CounterTaskRepository repository;

    public CounterTaskExecutionStateSynchronizerImpl(CounterTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateTask(CounterTaskExecutionState task) {
        CounterTask persistedTask = repository.findById(task.getId()).get();
        if(persistedTask == null) {
            logger.info("Task " + task.getId() + " has been already deleted. Nothing to update");
            return;
        }

        persistedTask.setStatus(task.getStatus().get());
        persistedTask.setX(task.getX().get());
    }

}
