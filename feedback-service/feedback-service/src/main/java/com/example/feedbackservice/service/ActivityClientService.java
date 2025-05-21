package com.example.feedbackservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ActivityClientService {
    private final RestTemplate restTemplate;

     @Autowired
    public ActivityClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean doesActivityExist(Long activityId) {
        try {
            String url = "http://activity-service/activities/" + activityId; 
            restTemplate.getForEntity(url, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found");
        }
    }
}

