package com.cln.challenge.controllers;

import com.cln.challenge.exceptions.TaskExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//TODO UNIT TEST ME!

@RestControllerAdvice
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        ErrorResponse response = new ErrorResponse();
        for (FieldError error : e.getFieldErrors()) {
            response.addError("Field: [" + error.getField() + "] " + error.getDefaultMessage());
        }
        return response.getResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleParameterCastExceptions(MethodArgumentTypeMismatchException e) {
        //when UUID cast fails.
        return new ErrorResponse().addError(e.getCause().getMessage())
                .getResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound() {
        logger.warn("Entity not found");
        return new ErrorResponse().addError("Entity not found")
                .getResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(IllegalStateException e) {
        logger.error("Illegal processing state", e);
        return new ErrorResponse().addError("Illegal processing state")
                .getResponse(HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockingException(OptimisticLockingFailureException e) {
        logger.error("Parallel processing exception, resource is locked.", e);
        return new ErrorResponse().addError("Parallel processing exception, resource is locked.")
                .getResponse(HttpStatus.LOCKED);
    }

    @ExceptionHandler(TaskExecutionException.class)
    public ResponseEntity<ErrorResponse> handleTaskExecutionException(TaskExecutionException e) {
        logger.error("Task Execution Exception", e);
        return new ErrorResponse().addError("Task Execution Exception").addError(e.getMessage())
                .getResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

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
