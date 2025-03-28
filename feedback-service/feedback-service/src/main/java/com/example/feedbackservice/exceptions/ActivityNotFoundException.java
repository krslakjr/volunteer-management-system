package com.example.feedbackservice.exceptions;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(Long id) {
        super("Activity not found with id " + id);
    }
}
