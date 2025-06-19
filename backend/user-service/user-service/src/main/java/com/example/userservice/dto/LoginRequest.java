package com.example.userservice.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data 
public class LoginRequest {

    @NotBlank(message = "Korisničko ime ne smije biti prazno")
    private String username;

    @NotBlank(message = "Lozinka ne smije biti prazna")
    private String password;
}