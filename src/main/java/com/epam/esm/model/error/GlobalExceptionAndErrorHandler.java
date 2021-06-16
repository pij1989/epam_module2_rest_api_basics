package com.epam.esm.model.error;

import com.epam.esm.model.exception.BadRequestException;
import com.epam.esm.model.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionAndErrorHandler {

    private final ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    public GlobalExceptionAndErrorHandler(ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({NotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<Object> customHandleNotFound(Exception e) {
        CustomError customError = new CustomError();
        if (e instanceof NotFoundException) {
//            customError.setErrorMessage(e.getMessage());
            customError.setErrorMessage(messageSource.getMessage("error.404.tag", null, LocaleContextHolder.getLocale()));
        } else {
//            customError.setErrorMessage("Resource not found");
            customError.setErrorMessage(messageSource.getMessage("error.404.common", null, LocaleContextHolder.getLocale()));
        }
        customError.setErrorCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<Object> customHandleBadRequest(Exception e) {
        CustomError customError = new CustomError();
        if (e instanceof BadRequestException) {
            customError.setErrorMessage(e.getMessage());
        } else {
            customError.setErrorMessage("Bad request");
        }
        customError.setErrorCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> customHandleMethodNotAllowed(Exception e) {
        CustomError customError = new CustomError();
        customError.setErrorMessage("Method not allowed");
        customError.setErrorCode(Integer.toString(HttpStatus.METHOD_NOT_ALLOWED.value()));
        return new ResponseEntity<>(customError, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleInternalServerError() {
        CustomError customError = new CustomError();
        customError.setErrorMessage("Internal server error");
        customError.setErrorCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
