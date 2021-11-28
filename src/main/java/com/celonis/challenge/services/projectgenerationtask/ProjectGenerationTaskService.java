package com.celonis.challenge.services.projectgenerationtask;

import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectGenerationTaskService {

    ProjectGenerationTask getTask(String taskId);

    List<ProjectGenerationTask> listTasks();

    ProjectGenerationTask createTask(ProjectGenerationTaskModel model);

    ProjectGenerationTask update(String taskId, ProjectGenerationTaskModel updateModel);

    void delete(String taskId);

    void executeTask(String taskId);

    ResponseEntity<FileSystemResource> getTaskResultFile(String taskId);
}
