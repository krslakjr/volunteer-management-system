package com.example.feedbackservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserClientService {
    private final RestTemplate restTemplate;

    @Value("http://localhost:8082")
    private String userServiceUrl;

    public UserClientService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public boolean isValidVolunteer(Long userId) {
        try {
            String url = userServiceUrl + "/users/" + userId;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map userData = response.getBody();
            return userData != null && "Volunteer".equals(userData.get("roleName"));
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}

