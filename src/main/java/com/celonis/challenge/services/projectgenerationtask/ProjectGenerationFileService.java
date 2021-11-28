package com.celonis.challenge.services.projectgenerationtask;

import org.springframework.core.io.FileSystemResource;

public interface ProjectGenerationFileService {

    FileSystemResource getTaskFileFromStorage(String storageLocation);

    String createAndStoreTaskFile(String taskId);

}