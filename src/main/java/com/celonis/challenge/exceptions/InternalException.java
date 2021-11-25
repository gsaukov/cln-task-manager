package com.celonis.challenge.exceptions;

public class InternalException extends RuntimeException {

    public InternalException(String message, Exception e) {
        super(message, e);
    }

    public InternalException(String message) {
        super(message);
    }

}
