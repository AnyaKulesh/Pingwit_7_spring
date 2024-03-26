package com.example.springdemo.exception;

public class JokeAPIException extends RuntimeException {
    public JokeAPIException(String message) {
        super(message);
    }
}
