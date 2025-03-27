package com.example.userservice;

import com.example.userservice.models.VolunteerCertificate;
import com.example.userservice.repository.VolunteerCertificateRepository;
import com.example.userservice.service.VolunteerCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VolunteerCertificateServiceTest {

    @Mock
    private VolunteerCertificateRepository volunteerCertificateRepository;

    @InjectMocks
    private VolunteerCertificateService volunteerCertificateService;

    private VolunteerCertificate volunteerCertificate;

    @BeforeEach
    public void setUp() {
        volunteerCertificate = new VolunteerCertificate();
        volunteerCertificate.setCertificateId(1L);
        volunteerCertificate.setCertificatePdfLink("link");
    }

    @Test
    public void testGetAllVolunteerCertificates() {
        when(volunteerCertificateRepository.findAll()).thenReturn(Arrays.asList(volunteerCertificate));

        List<VolunteerCertificate> certificates = volunteerCertificateService.getAllVolunteerCertificates();

        assertNotNull(certificates);
        assertEquals(1, certificates.size());
        assertEquals("link", certificates.get(0).getCertificatePdfLink());
    }

    @Test
    public void testGetCertificatesByUserId_Found() {
        when(volunteerCertificateRepository.findByUser_UserId(1L)).thenReturn(Arrays.asList(volunteerCertificate));

        List<VolunteerCertificate> certificates = volunteerCertificateService.getCertificatesByUserId(1L);

        assertNotNull(certificates);
        assertEquals(1, certificates.size());
        assertEquals("link", certificates.get(0).getCertificatePdfLink());
    }

    @Test
    public void testGetCertificatesByUserId_NotFound() {
        when(volunteerCertificateRepository.findByUser_UserId(1L)).thenReturn(Arrays.asList());

        List<VolunteerCertificate> certificates = volunteerCertificateService.getCertificatesByUserId(1L);

        assertTrue(certificates.isEmpty());
    }

    @Test
    public void testAddVolunteerCertificate() {
        when(volunteerCertificateRepository.save(volunteerCertificate)).thenReturn(volunteerCertificate);

        VolunteerCertificate createdCertificate = volunteerCertificateService.addVolunteerCertificate(volunteerCertificate);

        assertNotNull(createdCertificate);
        assertEquals(volunteerCertificate.getCertificateId(), createdCertificate.getCertificateId());
        assertEquals("link", createdCertificate.getCertificatePdfLink());
        verify(volunteerCertificateRepository, times(1)).save(volunteerCertificate);
    }

    @Test
    public void testDeleteVolunteerCertificate_Success() {
        doNothing().when(volunteerCertificateRepository).deleteById(1L);

        volunteerCertificateService.deleteVolunteerCertificate(1L);

        verify(volunteerCertificateRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteVolunteerCertificate_NotFound() {
        doThrow(new RuntimeException("Certificate not found with id 1")).when(volunteerCertificateRepository).deleteById(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> volunteerCertificateService.deleteVolunteerCertificate(1L));

        assertEquals("Certificate not found with id 1", exception.getMessage());
    }
}
