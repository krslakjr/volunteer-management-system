package com.example.participationservice.service;

import com.example.participationservice.models.Certificate;
import com.example.participationservice.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }
     public void saveCertificate(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    public Optional<Certificate> getCertificateById(Long id) {
        return certificateRepository.findById(id);
    }

    public Certificate createCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    public Certificate updateCertificate(Long id, Certificate certificateDetails) {
        Optional<Certificate> optionalCertificate = certificateRepository.findById(id);

        if (optionalCertificate.isPresent()) {
            Certificate certificate = optionalCertificate.get();
            certificate.setVolunteer(certificateDetails.getVolunteer());
            certificate.setActivity(certificateDetails.getActivity());
            certificate.setIssueDate(certificateDetails.getIssueDate());
            certificate.setCertificateStatus(certificateDetails.getCertificateStatus());
            return certificateRepository.save(certificate);
        }
        return null;
    }

    public boolean deleteCertificate(Long id) {
        if (certificateRepository.existsById(id)) {
            certificateRepository.deleteById(id);
            return true;
        }
        return false;
    }
}