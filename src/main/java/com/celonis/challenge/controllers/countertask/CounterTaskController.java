package com.celonis.challenge.controllers.countertask;

import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.services.countertask.execution.CounterTaskExecutionService;
import com.celonis.challenge.services.countertask.CounterTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countertasks")
public class CounterTaskController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CounterTaskService counterTaskService;

    private final CounterTaskExecutionService executionService;

    public CounterTaskController(CounterTaskService counterTaskService, CounterTaskExecutionService executionService) {
        this.counterTaskService = counterTaskService;
        this.executionService = executionService;
    }

    @GetMapping("/")
    public List<CounterTaskModel> listTasks() {
        return counterTaskService.listTasks().stream()
                .map(this::toModel).collect(Collectors.toList());
    }

    @PostMapping("/")
    public CounterTaskModel createTask(@RequestBody @Valid CounterTaskModel ctTask) {
        CounterTask createdTask = counterTaskService.createTask(ctTask);
        logger.info("Created task id: " + createdTask.getId());
        return toModel(createdTask);
    }

    @GetMapping("/{taskId}")
    public CounterTaskModel getTask(@PathVariable String taskId) {
        return toModel(counterTaskService.getTask(taskId));
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskId) {
        counterTaskService.delete(taskId);
        logger.info("Deleted task id: " + taskId);
    }

    @PostMapping("/{taskId}/execute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void executeTask(@PathVariable String taskId) {
        counterTaskService.executeTask(taskId);
        logger.info("Execution started task id: " + taskId);
    }

    @PostMapping("/{taskId}/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopTask(@PathVariable String taskId) {
        counterTaskService.stopTask(taskId);
        logger.info("Execution stopped task id: " + taskId);
    }

    private CounterTaskModel toModel(CounterTask counterTask) {
        CounterTaskModel model = new CounterTaskModel();
        model.setId(counterTask.getId());
        model.setName(counterTask.getCounterTaskName());
        model.setX(counterTask.getX());
        model.setY(counterTask.getY());
        model.setCreatedAt(counterTask.getCreatedAt());
        model.setUpdateAt(counterTask.getCreatedAt());
        model.setStatus(counterTask.getStatus().toString());
        return model;
    }

}
