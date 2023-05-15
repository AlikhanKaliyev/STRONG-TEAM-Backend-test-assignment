package com.example.strong_backend_test_assignment.exception;

import com.example.strong_backend_test_assignment.domain.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleNewsPaginationException(Exception e) {
        return new ResponseEntity(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
