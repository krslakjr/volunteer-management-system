package com.example.participationservice.service;

import com.example.common.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

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

            ResponseEntity<UserDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    UserDTO.class
            );
            UserDTO user = response.getBody();
            if (user == null) return false;
            Set<String> roles = user.getRoles();
            return roles != null && roles.contains("ROLE_VOLUNTEER");
        }  catch (HttpClientErrorException e) {
        System.out.println("USER-SERVICE error: " + e.getStatusCode());
        System.out.println("BODY: " + e.getResponseBodyAsString());
        return false;
        } catch (Exception e) {
        e.printStackTrace();  // za sve ostale greške
        return false;
    }
}
}