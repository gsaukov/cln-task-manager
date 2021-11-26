package com.celonis.challenge.services.projectgeneration;

import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    ProjectGenerationTask getTask(String taskId);

    List<ProjectGenerationTask> listTasks();

    ProjectGenerationTask createTask(ProjectGenerationTask pgTask);

    ProjectGenerationTask update(String taskId, ProjectGenerationTask updatePgTask);

    void delete(String taskId);

    void executeTask(String taskId);

    ResponseEntity<FileSystemResource> getTaskResultFile(String taskId);
}
