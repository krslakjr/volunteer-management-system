package com.example.userservice;

import com.example.userservice.controller.VolunteerCertificateController;
import com.example.userservice.models.VolunteerCertificate;
import com.example.userservice.service.VolunteerCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VolunteerCertificateControllerTest {

    @Mock
    private VolunteerCertificateService volunteerCertificateService;

    @InjectMocks
    private VolunteerCertificateController volunteerCertificateController;

    private VolunteerCertificate volunteerCertificate;

    @BeforeEach
    public void setUp() {
        volunteerCertificate = new VolunteerCertificate();
        volunteerCertificate.setCertificateId(1L);
        volunteerCertificate.setCertificateDate(new Date());
        volunteerCertificate.setCertificatePdfLink("certificate.pdf");
        volunteerCertificate.setIssuedAt(new Date());
    }

    @Test
    public void testGetCertificatesByUserId_Found() {
        when(volunteerCertificateService.getCertificatesByUserId(1L)).thenReturn(Arrays.asList(volunteerCertificate));

        ResponseEntity<List<VolunteerCertificate>> response = volunteerCertificateController.getCertificatesByUserId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testAddVolunteerCertificate() {
        when(volunteerCertificateService.addVolunteerCertificate(any(VolunteerCertificate.class))).thenReturn(volunteerCertificate);

        ResponseEntity<VolunteerCertificate> response = volunteerCertificateController.addVolunteerCertificate(volunteerCertificate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("certificate.pdf", response.getBody().getCertificatePdfLink());
    }

    @Test
    public void testDeleteVolunteerCertificate() {
        doNothing().when(volunteerCertificateService).deleteVolunteerCertificate(1L);

        ResponseEntity<Void> response = volunteerCertificateController.deleteVolunteerCertificate(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(volunteerCertificateService, times(1)).deleteVolunteerCertificate(1L);
    }
}
