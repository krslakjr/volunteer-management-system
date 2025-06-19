package com.example.participationservice;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.participationservice.models.Certificate;
import com.example.participationservice.repository.CertificateRepository;
import com.example.participationservice.service.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private CertificateService certificateService;

    private Certificate certificate;

    @BeforeEach
    void setUp() {
        certificate = new Certificate();
        certificate.setCertificateId(1L);
        certificate.setCertificateStatus("Issued");
    }

    @Test
    void testGetAllCertificates() {
        List<Certificate> certificates = Arrays.asList(certificate);
        when(certificateRepository.findAll()).thenReturn(certificates);
        List<Certificate> result = certificateService.getAllCertificates();
        assertEquals(1, result.size());
        verify(certificateRepository, times(1)).findAll();
    }

    @Test
    void testGetCertificateById_Found() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
        Optional<Certificate> result = certificateService.getCertificateById(1L);
        assertTrue(result.isPresent());
        assertEquals("Issued", result.get().getCertificateStatus());
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCertificateById_NotFound() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Certificate> result = certificateService.getCertificateById(1L);
        assertFalse(result.isPresent());
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCertificate() {
        when(certificateRepository.save(any(Certificate.class))).thenReturn(certificate);
        Certificate result = certificateService.createCertificate(certificate);
        assertNotNull(result);
        assertEquals("Issued", result.getCertificateStatus());
        verify(certificateRepository, times(1)).save(certificate);
    }

    @Test
    void testUpdateCertificate_Found() {
        Certificate updatedCertificate = new Certificate();
        updatedCertificate.setCertificateStatus("Updated");

        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificateRepository.save(any(Certificate.class))).thenReturn(updatedCertificate);

        Certificate result = certificateService.updateCertificate(1L, updatedCertificate);
        assertNotNull(result);
        assertEquals("Updated", result.getCertificateStatus());
        verify(certificateRepository, times(1)).save(any(Certificate.class));
    }

    @Test
    void testUpdateCertificate_NotFound() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.empty());
        Certificate result = certificateService.updateCertificate(1L, certificate);
        assertNull(result);
        verify(certificateRepository, never()).save(any(Certificate.class));
    }

    @Test
    void testDeleteCertificate_Found() {
        when(certificateRepository.existsById(1L)).thenReturn(true);
        boolean result = certificateService.deleteCertificate(1L);
        assertTrue(result);
        verify(certificateRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCertificate_NotFound() {
        when(certificateRepository.existsById(1L)).thenReturn(false);
        boolean result = certificateService.deleteCertificate(1L);
        assertFalse(result);
        verify(certificateRepository, never()).deleteById(1L);
    }
}
