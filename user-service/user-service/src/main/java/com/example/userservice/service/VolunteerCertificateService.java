package com.example.userservice.service;

import com.example.userservice.models.VolunteerCertificate;
import com.example.userservice.repository.VolunteerCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerCertificateService {

    @Autowired
    private VolunteerCertificateRepository volunteerCertificateRepository;

    // Dohvati sve VolunteerCertificates
    public List<VolunteerCertificate> getAllVolunteerCertificates() {
        return volunteerCertificateRepository.findAll();
    }

    // Dohvati VolunteerCertificates za određenog korisnika
    public List<VolunteerCertificate> getCertificatesByUserId(Long userId) {
        return volunteerCertificateRepository.findByUser_UserId(userId);
    }

    // Dodaj novi VolunteerCertificate
    public VolunteerCertificate addVolunteerCertificate(VolunteerCertificate volunteerCertificate) {
        return volunteerCertificateRepository.save(volunteerCertificate);
    }

    // Obriši VolunteerCertificate
    public void deleteVolunteerCertificate(Long certificateId) {
        volunteerCertificateRepository.deleteById(certificateId);
    }
}
