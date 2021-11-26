package com.celonis.challenge.services.projectgeneration;

import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTask;
import com.celonis.challenge.model.projectgenerationtask.ProjectGenerationTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {


    private static final String ATTACHMENT = "attachment";
    private static final String CHALLENGE_ZIP = "challenge.zip";

    private final ProjectGenerationTaskRepository pgtRepository;

    private final FileService fileService;

    public TaskServiceImpl(ProjectGenerationTaskRepository pgtRepository,
                           FileService fileService) {
        this.pgtRepository = pgtRepository;
        this.fileService = fileService;
    }

    @Override
    public ProjectGenerationTask getTask(String taskId) {
        return pgtRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ProjectGenerationTask> listTasks() {
        return pgtRepository.findAll();
    }

    @Override
    public ProjectGenerationTask createTask(ProjectGenerationTask pgTask) {
        pgTask.setCreationDate(new Date());
        return pgtRepository.save(pgTask);
    }

    @Override
    public ProjectGenerationTask update(String taskId, ProjectGenerationTask updatePgTask) {
        ProjectGenerationTask existingPgTask = getTask(taskId);
        existingPgTask.setName(updatePgTask.getName());
        return existingPgTask;
    }

    @Override
    public void delete(String taskId) {
        pgtRepository.deleteById(getTask(taskId).getId());
    }

    @Override
    public void executeTask(String taskId) {
        ProjectGenerationTask pgTask = getTask(taskId);
        String filePath = fileService.createAndStoreTaskFile(pgTask.getId());
        pgTask.setStorageLocation(filePath);
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

}
