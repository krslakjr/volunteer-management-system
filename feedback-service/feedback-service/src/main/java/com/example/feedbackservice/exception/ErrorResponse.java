package com.example.feedbackservice.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String errorType;
    private String message;
    private String field;

    public ErrorResponse(String errorType, String message, String field) {
        this.timestamp = LocalDateTime.now();
        this.errorType = errorType;
        this.message = message;
        this.field = field;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
