package com.example.springdemo.exception;

public class PingwitNotFoundException extends RuntimeException {
    public PingwitNotFoundException(String message) {
        super(message);
    }
}
