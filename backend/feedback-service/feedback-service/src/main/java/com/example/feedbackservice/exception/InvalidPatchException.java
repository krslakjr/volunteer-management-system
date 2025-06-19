package com.example.feedbackservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPatchException extends RuntimeException {
    public InvalidPatchException(String message) {
        super(message);
    }
}