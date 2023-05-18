package com.benratti.openpp.api.rest;

import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler
    public ProblemDetail handle(IllegalStateException e) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("https://api.open-pp.com/errors/illegal-state"));
        return problemDetail;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ProblemDetail handle(ResourceNotFoundException e) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("https://api.open-pp.com/errors/illegal-state"));
        return problemDetail;
    }


}
