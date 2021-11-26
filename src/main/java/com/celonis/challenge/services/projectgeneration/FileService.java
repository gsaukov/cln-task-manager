package com.celonis.challenge.services.projectgeneration;

import com.celonis.challenge.exceptions.TaskExecutionException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class FileService {

    private static final String CHALLENGE_ZIP = "challenge.zip";
    private static final String FILE_SUFFIX = ".zip";

    public FileSystemResource getTaskFileFromStorage(String storageLocation) {
        File inputFile = new File(storageLocation);
        if (!inputFile.exists()) {
            throw new IllegalStateException("File not generated yet");
        } else {
            return new FileSystemResource(inputFile);
        }
    }

    public String createAndStoreTaskFile(String taskId) {
        URL url = getSourceFileUrl();
        File outputFile = createOutputFile(taskId);
        try (InputStream is = url.openStream(); OutputStream os = new FileOutputStream(outputFile)) {
            IOUtils.copy(is, os);
            return outputFile.getAbsolutePath();
        } catch (IOException e) {
            throw new TaskExecutionException("Task File creation failed", e);
        }
    }

    private File createOutputFile(String taskId) {
        try {
            File outputFile = File.createTempFile(taskId, FILE_SUFFIX);
            outputFile.deleteOnExit();
            return outputFile;
        } catch (IOException e) {
            throw new TaskExecutionException("Task File creation failed", e);
        }
    }

    private URL getSourceFileUrl() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(CHALLENGE_ZIP);
        if (url == null) {
            throw new TaskExecutionException("Zip file not found");
        } else {
            return url;
        }
    }
}
