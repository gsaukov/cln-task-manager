package com.cln.challenge.services.projectgenerationtask;

import org.springframework.core.io.FileSystemResource;

import java.util.UUID;

public interface ProjectGenerationFileService {

    FileSystemResource getTaskFileFromStorage(String storageLocation);

    String createAndStoreTaskFile(UUID taskId);

}
