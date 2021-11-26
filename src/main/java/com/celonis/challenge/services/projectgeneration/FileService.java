package com.celonis.challenge.services.projectgeneration;

import org.springframework.core.io.FileSystemResource;

public interface FileService {

    FileSystemResource getTaskFileFromStorage(String storageLocation);

    String createAndStoreTaskFile(String taskId);

}
