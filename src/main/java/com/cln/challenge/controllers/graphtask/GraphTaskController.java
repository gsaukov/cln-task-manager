package com.cln.challenge.controllers.graphtask;

import com.cln.challenge.model.graphtask.entity.GraphTask;
import com.cln.challenge.services.graphtask.GraphTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/graphtasks")
public class GraphTaskController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GraphTaskService graphTaskService;

    public GraphTaskController(GraphTaskService graphTaskService) {
        this.graphTaskService = graphTaskService;
    }

    @GetMapping("/")
    public List<GraphTaskModel> listTasks() {
        return graphTaskService.listTasks().stream()
                .map(this::toModel).collect(Collectors.toList());
    }

    @PostMapping("/")
    public GraphTaskModel createTask(@RequestBody @Valid GraphTaskModel ctTask) {
        GraphTask createdTask = graphTaskService.createTask(ctTask);
        logger.info("Created graph task id: " + createdTask.getId());
        return toModel(createdTask);
    }

    @GetMapping("/{taskId}")
    public GraphTaskModel getTask(@PathVariable UUID taskId) {
        return toModel(graphTaskService.getTask(taskId));
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID taskId) {
        graphTaskService.delete(taskId);
        logger.info("Deleted graph task id: " + taskId);
    }

    @PostMapping("/{taskId}/execute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void executeTask(@PathVariable UUID taskId) {
        graphTaskService.executeTask(taskId);
    }

    @PostMapping("/{taskId}/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopTask(@PathVariable UUID taskId) {
        graphTaskService.stopTask(taskId);
        logger.info("Execution stopped graph task id: " + taskId);
    }


    private GraphTaskModel toModel(GraphTask graphTask) {
        GraphTaskModel model = new GraphTaskModel();
        return model;
    }

}
