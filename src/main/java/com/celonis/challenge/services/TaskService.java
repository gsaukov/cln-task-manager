package com.celonis.challenge.services;

import com.celonis.challenge.exceptions.NotFoundException;
import com.celonis.challenge.model.ProjectGenerationTask;
import com.celonis.challenge.model.ProjectGenerationTaskRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional // All entities are managed now.
public class TaskService {

    private static final String ATTACHMENT = "attachment";
    private static final String CHALLENGE_ZIP = "challenge.zip";

    private final ProjectGenerationTaskRepository projectGenerationTaskRepository;

    private final FileService fileService;

    public TaskService(ProjectGenerationTaskRepository projectGenerationTaskRepository,
                       FileService fileService) {
        this.projectGenerationTaskRepository = projectGenerationTaskRepository;
        this.fileService = fileService;
    }

    public ProjectGenerationTask getTask(String taskId) {
        Optional<ProjectGenerationTask> projectGenerationTask = projectGenerationTaskRepository.findById(taskId);
        return projectGenerationTask.orElseThrow(NotFoundException::new);
    }

    public List<ProjectGenerationTask> listTasks() {
        return projectGenerationTaskRepository.findAll();
    }

    public ProjectGenerationTask createTask(ProjectGenerationTask projectGenerationTask) {
        projectGenerationTask.setId(null);
        projectGenerationTask.setCreationDate(new Date());
        return projectGenerationTaskRepository.save(projectGenerationTask);
    }

    public ProjectGenerationTask update(String taskId, ProjectGenerationTask newTask) {
        ProjectGenerationTask existingTask = getTask(taskId);
        existingTask.setCreationDate(newTask.getCreationDate());
        existingTask.setName(newTask.getName());
        return existingTask;
    }

    public void delete(String taskId) {
        projectGenerationTaskRepository.deleteById(taskId);
    }

    public void executeTask(String taskId) {
        ProjectGenerationTask task = getTask(taskId);
        String filePath = fileService.createAndStoreTaskFile(task.getId());
        task.setStorageLocation(filePath);
    }

    public ResponseEntity<FileSystemResource> getTaskResultFile(String taskId) {
        ProjectGenerationTask task = projectGenerationTaskRepository.getById(taskId);
        FileSystemResource resource = fileService.getFileFromStorage(task.getStorageLocation());
        return prepareFileResponse(resource);
    }

    private ResponseEntity<FileSystemResource> prepareFileResponse(FileSystemResource resource) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData(ATTACHMENT, CHALLENGE_ZIP);
        return new ResponseEntity<>(resource, respHeaders, HttpStatus.OK);
    }

}
