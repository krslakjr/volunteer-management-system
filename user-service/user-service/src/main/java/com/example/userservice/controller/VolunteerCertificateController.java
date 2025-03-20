package com.example.userservice.controller;

import com.example.userservice.models.VolunteerCertificate;
import com.example.userservice.service.VolunteerCertificateService;
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

    // Dohvati sve VolunteerCertificates
    @GetMapping
    public List<VolunteerCertificate> getAllVolunteerCertificates() {
        return volunteerCertificateService.getAllVolunteerCertificates();
    }

    // Dohvati VolunteerCertificates za određenog korisnika
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VolunteerCertificate>> getCertificatesByUserId(@PathVariable Long userId) {
        List<VolunteerCertificate> certificates = volunteerCertificateService.getCertificatesByUserId(userId);
        if (certificates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    // Dodaj novi VolunteerCertificate
    @PostMapping
    public ResponseEntity<VolunteerCertificate> addVolunteerCertificate(@RequestBody VolunteerCertificate volunteerCertificate) {
        VolunteerCertificate createdCertificate = volunteerCertificateService.addVolunteerCertificate(volunteerCertificate);
        return new ResponseEntity<>(createdCertificate, HttpStatus.CREATED);
    }

    // Obriši VolunteerCertificate
    @DeleteMapping("/{certificateId}")
    public ResponseEntity<Void> deleteVolunteerCertificate(@PathVariable Long certificateId) {
        volunteerCertificateService.deleteVolunteerCertificate(certificateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
