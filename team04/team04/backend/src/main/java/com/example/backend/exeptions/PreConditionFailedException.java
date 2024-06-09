package com.example.backend.exeptions;

public class PreConditionFailedException extends RuntimeException {
    public PreConditionFailedException(String message) {
        super(message);
    }
}
