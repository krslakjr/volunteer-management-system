package com.example.participationservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String field; 

   
    public ResourceNotFoundException(String message, String field) {
        super(message);
        this.field = field;
    }

    
    public ResourceNotFoundException(String message) {
        super(message);
        this.field = null; 
    }

    public String getField() {
        return field;
    }
}
