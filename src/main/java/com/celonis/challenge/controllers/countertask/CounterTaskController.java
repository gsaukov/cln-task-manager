package com.celonis.challenge.controllers.countertask;

import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.services.countertask.CounterTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//TODO I have explicitly removed update method, because it would collide with execution operations and cause optimistic
// locking exceptions on COUNTER_TASK table. To enable user updates on COUNTER_TASK execution counter must be moved to another table.
// but that would make implementation even more complex.

@RestController
@RequestMapping("/api/v1/countertasks")
public class CounterTaskController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CounterTaskService counterTaskService;

    public CounterTaskController(CounterTaskService counterTaskService) {
        this.counterTaskService = counterTaskService;
    }

    @GetMapping("/")
    public List<CounterTaskModel> listTasks() {
        return counterTaskService.listTasks().stream()
                .map(this::toModel).collect(Collectors.toList());
    }

    @PostMapping("/")
    public CounterTaskModel createTask(@RequestBody @Valid CounterTaskModel ctTask) {
        CounterTask createdTask = counterTaskService.createTask(ctTask);
        logger.info("Created counter task id: " + createdTask.getId());
        return toModel(createdTask);
    }

    @GetMapping("/{taskId}")
    public CounterTaskModel getTask(@PathVariable UUID taskId) {
        return toModel(counterTaskService.getTask(taskId));
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID taskId) {
        counterTaskService.delete(taskId);
        logger.info("Deleted counter task id: " + taskId);
    }

    @PostMapping("/{taskId}/execute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void executeTask(@PathVariable UUID taskId) {
        counterTaskService.executeTask(taskId);
    }

    @PostMapping("/{taskId}/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopTask(@PathVariable UUID taskId) {
        counterTaskService.stopTask(taskId);
        logger.info("Execution stopped counter task id: " + taskId);
    }

    @GetMapping("/{taskId}/taskstate")
    public SseEmitter getTaskExecutionState(@PathVariable UUID taskId) {
        return counterTaskService.subscribeToExecutionEvents(taskId);
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
