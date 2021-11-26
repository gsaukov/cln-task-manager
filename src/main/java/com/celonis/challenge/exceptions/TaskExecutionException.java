package com.celonis.challenge.exceptions;

public class TaskExecutionException extends RuntimeException {

    public TaskExecutionException(String message, Exception e) {
        super(message, e);
    }

    public TaskExecutionException(String message) {
        super(message);
    }

}
