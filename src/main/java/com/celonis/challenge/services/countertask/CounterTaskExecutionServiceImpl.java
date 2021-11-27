package com.celonis.challenge.services.countertask;

import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.model.countertask.repository.CounterTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CounterTaskExecutionServiceImpl implements CounterTaskExecutionService{

    public CounterTaskExecutionServiceImpl() {

    }

    @Override
    public void executeTask(String taskId) {

    }

    @Override
    public void stopTask(String taskId) {

    }
}
