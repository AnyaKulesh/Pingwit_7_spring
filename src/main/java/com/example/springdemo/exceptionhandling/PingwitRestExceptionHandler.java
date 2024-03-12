package com.example.springdemo.exceptionhandling;

import com.example.springdemo.exception.PingwitNotFoundException;
import com.example.springdemo.exception.PingwitValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class PingwitRestExceptionHandler {
    @ExceptionHandler(PingwitNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(PingwitNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PingwitValidationException.class)
    public ResponseEntity<String> handleValidationException(PingwitValidationException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.toString());
    }
}
