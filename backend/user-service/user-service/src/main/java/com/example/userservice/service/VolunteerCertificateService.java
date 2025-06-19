package com.example.userservice.service;

import com.example.userservice.models.VolunteerCertificate;
import com.example.userservice.repository.VolunteerCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class VolunteerCertificateService {

    @Autowired
    private VolunteerCertificateRepository volunteerCertificateRepository;

    public List<VolunteerCertificate> getAllVolunteerCertificates() {
        return volunteerCertificateRepository.findAll();
    }

    public List<VolunteerCertificate> getAllVolunteersCertificates(Pageable pageable) {
        Page<VolunteerCertificate> page = volunteerCertificateRepository.findAll(pageable);
        return page.getContent();
    }

    public List<VolunteerCertificate> getCertificatesByUserId(Long userId) {
        return volunteerCertificateRepository.findByUser_UserId(userId);
    }

    public VolunteerCertificate addVolunteerCertificate(VolunteerCertificate volunteerCertificate) {
        return volunteerCertificateRepository.save(volunteerCertificate);
    }

    public void deleteVolunteerCertificate(Long certificateId) {
        volunteerCertificateRepository.deleteById(certificateId);
    }
}
