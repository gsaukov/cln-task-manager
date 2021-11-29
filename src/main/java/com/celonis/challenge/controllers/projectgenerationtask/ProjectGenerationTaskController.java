package com.celonis.challenge.controllers.projectgenerationtask;

import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.services.projectgenerationtask.ProjectGenerationTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class ProjectGenerationTaskController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProjectGenerationTaskService taskService;

    public ProjectGenerationTaskController(ProjectGenerationTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public List<ProjectGenerationTaskModel> listTasks() {
        return taskService.listTasks().stream()
                .map(this::toModel).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ProjectGenerationTaskModel createTask(@RequestBody @Valid ProjectGenerationTaskModel model) {
        ProjectGenerationTask task = taskService.createTask(model);
        logger.info("Created project generation task id: " + task.getId());
        return toModel(task);
    }

    @GetMapping("/{taskId}")
    public ProjectGenerationTaskModel getTask(@PathVariable String taskId) {
        return toModel(taskService.getTask(taskId));
    }

    @PutMapping(path = "/{taskId}")
    public ProjectGenerationTaskModel updateTask(@PathVariable String taskId,
                                            @RequestBody @Valid ProjectGenerationTaskModel updateModel) {
        ProjectGenerationTask existingTask = taskService.update(taskId, updateModel);
        logger.info("Updated project generation task id: " + existingTask.getId());
        return toModel(existingTask);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskId) {
        taskService.delete(taskId);
        logger.info("Deleted project generation task id: " + taskId);
    }

    @PostMapping("/{taskId}/execute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void executeTask(@PathVariable String taskId) {
        taskService.executeTask(taskId);
        logger.info("Executed project generation task id: " + taskId);
    }

    @GetMapping("/{taskId}/result")
    public ResponseEntity<FileSystemResource> getResult(@PathVariable String taskId) {
        return taskService.getTaskResultFile(taskId);
    }

    private ProjectGenerationTaskModel toModel(ProjectGenerationTask task) {
        ProjectGenerationTaskModel model = new ProjectGenerationTaskModel();
        model.setId(task.getId());
        model.setName(task.getName());
        model.setCreationDate(task.getCreationDate());
        return model;
    }

}
