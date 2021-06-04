package com.epam.esm.model.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
