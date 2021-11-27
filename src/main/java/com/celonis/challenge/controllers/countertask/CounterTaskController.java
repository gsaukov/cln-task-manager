package com.celonis.challenge.controllers.countertask;

import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.services.countertask.CounterTaskExecutionService;
import com.celonis.challenge.services.countertask.CounterTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<CounterTask> listTasks() {
        return counterTaskService.listTasks();
    }

    @PostMapping("/")
    public CounterTask createTask(@RequestBody @Valid CounterTask ctTask) {
        counterTaskService.createTask(ctTask);
        logger.info("Created task id: " + ctTask.getId());
        return ctTask;
    }

    @GetMapping("/{taskId}")
    public CounterTask getTask(@PathVariable String taskId) {
        return counterTaskService.getTask(taskId);
    }

    @PutMapping(path = "/{taskId}")
    public CounterTask updateTask(@PathVariable String taskId,
                                            @RequestBody @Valid CounterTask updatePgTask) {
        CounterTask existingPgTask = counterTaskService.update(taskId, updatePgTask);
        logger.info("Updated task id: " + existingPgTask.getId());
        return existingPgTask;
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
        executionService.executeTask(taskId);
        logger.info("Execution started task id: " + taskId);
    }

    @PostMapping("/{taskId}/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopTask(@PathVariable String taskId) {
        executionService.stopTask(taskId);
        logger.info("Execution stopped task id: " + taskId);
    }

}
