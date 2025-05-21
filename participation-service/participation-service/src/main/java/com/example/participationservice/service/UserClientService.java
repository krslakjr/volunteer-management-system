package com.example.participationservice.service;

import com.example.common.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class UserClientService {

    private final RestTemplate restTemplate;

    @Autowired
    public UserClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValidVolunteer(Long userId) {
        try {
            // Use the service name instead of the hardcoded URL
            String url = "http://USER-SERVICE/users/" + userId;
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
            UserDTO user = response.getBody();
            return user != null && "Volunteer".equalsIgnoreCase(user.getRoleName());
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}