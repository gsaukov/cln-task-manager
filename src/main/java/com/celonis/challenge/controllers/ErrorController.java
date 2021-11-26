package com.celonis.challenge.controllers;

import com.celonis.challenge.exceptions.TaskExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        ErrorResponse response = new ErrorResponse();
        for (FieldError error : e.getFieldErrors()) {
            response.addError("Field: [" + error.getField() + "] " + error.getDefaultMessage());
        }
        return response.getResponse(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound() {
        logger.warn("Entity not found");
        return new ErrorResponse().addError("Entity not found")
                .getResponse(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(IllegalStateException e) {
        logger.error("Illegal processing state", e);
        return new ErrorResponse().addError("Illegal processing state")
                .getResponse(HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler(TaskExecutionException.class)
    public ResponseEntity<ErrorResponse> handleTaskExecutionException(TaskExecutionException e) {
        logger.error("Task Execution Exception", e);
        return new ErrorResponse().addError("Task Execution Exception").addError(e.getMessage())
                .getResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("Unhandled Exception", e);
        return new ErrorResponse().addError("Unhandled Internal exception")
                .getResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class ErrorResponse {

        private HttpStatus status;
        private final List<String> messages = new ArrayList<>();

        public ErrorResponse addError(String error) {
            messages.add(error);
            return this;
        }

        public ResponseEntity<ErrorResponse> getResponse(HttpStatus status) {
            this.status = status;
            return new ResponseEntity(this, status);
        }

        public HttpStatus getStatus() {
            return status;
        }

        public List<String> getMessages() {
            return messages;
        }

    }
}
