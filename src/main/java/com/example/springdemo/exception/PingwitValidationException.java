package com.example.springdemo.exception;

import java.util.List;

public class PingwitValidationException extends RuntimeException{
    private final List<String> violations;

    public PingwitValidationException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }

    @Override
    public String toString() {
        return getMessage() + " : " +
                "violations=" + violations;
    }
}
