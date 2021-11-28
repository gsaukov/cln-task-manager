package com.celonis.challenge.services.projectgenerationtask;

import com.celonis.challenge.controllers.countertask.CounterTaskModel;
import com.celonis.challenge.controllers.projectgenerationtask.ProjectGenerationTaskModel;
import com.celonis.challenge.model.countertask.entity.CounterTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProjectGenerationTaskServiceImpl implements ProjectGenerationTaskService {


    private static final String ATTACHMENT = "attachment";
    private static final String CHALLENGE_ZIP = "challenge.zip";

    private final ProjectGenerationTaskRepository repository;

    private final ProjectGenerationFileService fileService;

    public ProjectGenerationTaskServiceImpl(ProjectGenerationTaskRepository repository,
                                            ProjectGenerationFileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @Override
    public ProjectGenerationTask getTask(String taskId) {
        return repository.findById(taskId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ProjectGenerationTask> listTasks() {
        return repository.findAll();
    }

    @Override
    public ProjectGenerationTask createTask(ProjectGenerationTaskModel model) {
        ProjectGenerationTask task = toTask(model);
        task.setCreationDate(new Date());
        return repository.save(task);
    }

    @Override
    public ProjectGenerationTask update(String taskId, ProjectGenerationTaskModel updateModel) {
        ProjectGenerationTask existingTask = getTask(taskId);
        existingTask.setName(updateModel.getName());
        return existingTask;
    }

    @Override
    public void delete(String taskId) {
        repository.deleteById(getTask(taskId).getId());
    }

    @Override
    public void executeTask(String taskId) {
        ProjectGenerationTask task = getTask(taskId);
        String filePath = fileService.createAndStoreTaskFile(task.getId());
        task.setStorageLocation(filePath);
    }

    @Override
    public ResponseEntity<FileSystemResource> getTaskResultFile(String taskId) {
        ProjectGenerationTask pgTask = getTask(taskId);
        if (pgTask.getStorageLocation() == null) {
            throw new IllegalStateException("File not generated yet");
        }
        FileSystemResource resource = fileService.getTaskFileFromStorage(pgTask.getStorageLocation());
        return prepareFileResponse(resource);
    }

    private ResponseEntity<FileSystemResource> prepareFileResponse(FileSystemResource resource) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData(ATTACHMENT, CHALLENGE_ZIP);
        return new ResponseEntity<>(resource, respHeaders, HttpStatus.OK);
    }

    private ProjectGenerationTask toTask(ProjectGenerationTaskModel model) {
        ProjectGenerationTask task = new ProjectGenerationTask();
        task.setName(model.getName());
        return task;
    }

}
