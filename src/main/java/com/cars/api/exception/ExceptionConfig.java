package com.cars.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.util.EmptyStackException;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            EmptyStackException.class
    })
    public ResponseEntity errorNotFound(Exception ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity errorBadRequest(Exception ex) {
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler({
            AccessDeniedException.class
    })
    public ResponseEntity accessDenied(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Access denied."));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        return new ResponseEntity<>(new ExceptionError("Operation not allowed"), HttpStatus.METHOD_NOT_ALLOWED);
    }
}

class ExceptionError implements Serializable {
    private String error;
    ExceptionError(String error){
        this.error = error;
    }
    public String getError(){
        return error;
    }
}
