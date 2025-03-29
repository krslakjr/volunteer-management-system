package com.example.userservice.controller;

import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.models.Permission;
import com.example.userservice.models.VolunteerCertificate;
import com.example.userservice.service.VolunteerCertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteercertificates")
public class VolunteerCertificateController {

    @Autowired
    private VolunteerCertificateService volunteerCertificateService;

    @GetMapping
    public List<VolunteerCertificate> getAllVolunteerCertificates() {
        return volunteerCertificateService.getAllVolunteerCertificates();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VolunteerCertificate>> getCertificatesByUserId(@PathVariable Long userId) {
        List<VolunteerCertificate> certificates = volunteerCertificateService.getCertificatesByUserId(userId);
        
        if (certificates.isEmpty()) {
            throw new ResourceNotFoundException("Volunteer certificates not found for user with id " + userId, "id");
        }
        
        return ResponseEntity.ok(certificates);
    }
    

    @PostMapping
    public ResponseEntity<VolunteerCertificate> addVolunteerCertificate(@Valid @RequestBody VolunteerCertificate volunteerCertificate) {
        VolunteerCertificate createdCertificate = volunteerCertificateService.addVolunteerCertificate(volunteerCertificate);
        return new ResponseEntity<>(createdCertificate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<Void> deleteVolunteerCertificate(@PathVariable Long certificateId) {
        volunteerCertificateService.deleteVolunteerCertificate(certificateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
