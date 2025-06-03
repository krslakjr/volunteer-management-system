package com.example.feedbackservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class UserClientService {
    private final RestTemplate restTemplate;

    @Autowired
    public UserClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValidVolunteer(Long userId) {
        try {
            String url = "http://user-service/users/" + userId;
            
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map userData = response.getBody();

            return userData != null && "Volunteer".equalsIgnoreCase(userData.get("roleName").toString());
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (HttpClientErrorException e) {
            return false; 
        } catch (Exception e) {
            return false; 
        }
    }
}