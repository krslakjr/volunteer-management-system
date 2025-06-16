package com.example.feedbackservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserClientService {
    private final RestTemplate restTemplate;

    @Autowired
    public UserClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValidVolunteer(Long userId, String jwt) {
        try {
            String url = "http://USER-SERVICE/api/users/" + userId;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwt);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Map.class
            );
            Map userData = response.getBody();
            if (userData == null) return false;
            Object rolesObj = userData.get("roles");
            Set<String> roles = new HashSet<>();

            if (rolesObj instanceof List<?>) {
                for (Object role : (List<?>) rolesObj) {
                    if (role instanceof String) {
                        roles.add((String) role);
                    }
                }
            }
            return roles.contains("ROLE_VOLUNTEER");
        } catch (HttpClientErrorException e) {
            System.out.println("USER-SERVICE error: " + e.getStatusCode());
            System.out.println("BODY: " + e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            e.printStackTrace();  // za sve ostale greške
            return false;
        }
    }
}