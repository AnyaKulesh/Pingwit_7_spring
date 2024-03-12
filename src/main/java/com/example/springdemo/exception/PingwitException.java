package com.example.springdemo.exception;

public class PingwitException extends RuntimeException{
    public PingwitException(String message) {
        super(message);
    }
}
