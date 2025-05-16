package com.example.feedbackservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ActivityClientService {
    private final RestTemplate restTemplate;

    @Value("http://localhost:8086")
    private String activityServiceUrl;

    public ActivityClientService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public boolean doesActivityExist(Long activityId) {
        try {
            String url = activityServiceUrl + "/activities/" + activityId;
            restTemplate.getForEntity(url, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found");
        }
    }
}

