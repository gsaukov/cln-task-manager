package com.celonis.challenge.services.projectgenerationtask;

import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ProjectGenerationTaskService {

    ProjectGenerationTask getTask(UUID taskId);

    List<ProjectGenerationTask> listTasks();

    ProjectGenerationTask createTask(ProjectGenerationTaskModel model);

    ProjectGenerationTask update(UUID taskId, ProjectGenerationTaskModel updateModel);

    void delete(UUID taskId);

    void executeTask(UUID taskId);

    ResponseEntity<FileSystemResource> getTaskResultFile(UUID taskId);
}
