package com.users.ws.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage notValidArgumentError = new ErrorMessage("Error", ex.getMessage());
        return new ResponseEntity<>( notValidArgumentError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {UserExistsException.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Error","User having username like this exists");
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
