package com.example.participationservice.service;

import com.example.common.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
            UserDTO user = response.getBody();
            return user != null && "Volunteer".equalsIgnoreCase(user.getRoleName());
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
