package com.example.userservice.controller;

import com.example.userservice.dto.JwtResponse;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.MessageResponse; // Potreban za slanje prilagođenih poruka

import com.example.userservice.security.jwt.JwtUtils;
import com.example.userservice.security.services.UserDetailsImpl;
import com.example.userservice.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Potreban za vraćanje specifičnih HTTP statusa
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException; // Za pogrešne kredencijale
import org.springframework.security.authentication.DisabledException;     // Ako je korisnik onemogućen
import org.springframework.security.authentication.LockedException;      // Ako je korisnik zaključan
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Ako korisničko ime ne postoji

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600) // CORS konfiguracija
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService; // Koristimo UserService za registraciju

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Endpoint za prijavu korisnika i izdavanje JWT tokena.
     * @param loginRequest Objekat koji sadrži korisničko ime i lozinku.
     * @return ResponseEntity sa JWT tokenom i korisničkim detaljima, ili porukom o grešci.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
            ));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Greška: Korisničko ime nije pronađeno."));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Greška: Netačna lozinka."));
        } catch (DisabledException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Greška: Vaš nalog je onemogućen."));
        } catch (LockedException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Greška: Vaš nalog je zaključan. Pokušajte ponovo kasnije."));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Došlo je do neočekivane greške prilikom prijave: " + e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            userService.createUser(registerRequest);
            return ResponseEntity.ok(new MessageResponse("Korisnik uspješno registrovan!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Greška: " + e.getMessage()));
        }
    }
}
