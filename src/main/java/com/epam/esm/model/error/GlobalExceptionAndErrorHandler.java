package com.epam.esm.model.error;

import com.epam.esm.model.exception.TagNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionAndErrorHandler {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Object> customHandleNotFound(Exception e) {
        CustomError customError = new CustomError();
        customError.setErrorMessage(e.getMessage());
        customError.setErrorCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoTFound() {
        CustomError customError = new CustomError();
        customError.setErrorMessage("Resource not found");
        customError.setErrorCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }
}
