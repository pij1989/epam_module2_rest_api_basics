package com.epam.esm.model.error;

import com.epam.esm.model.exception.BadRequestException;
import com.epam.esm.model.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionAndErrorHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> customHandleNotFound(Exception e) {
        CustomError customError = new CustomError();
        customError.setErrorMessage(e.getMessage());
        customError.setErrorCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> customHandleBadRequest(Exception e) {
        CustomError customError = new CustomError();
        customError.setErrorMessage(e.getMessage());
        customError.setErrorCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoTFound() {
        CustomError customError = new CustomError();
        customError.setErrorMessage("Resource not found");
        customError.setErrorCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }
}
