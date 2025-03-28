package com.example.feedbackservice.exceptions;

public class InvalidActivityException extends RuntimeException {
    public InvalidActivityException(String message) {
        super(message);
    }
}
