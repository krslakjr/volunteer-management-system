package com.example.participationservice;

import com.example.participationservice.controller.CertificateController;
import com.example.participationservice.models.Certificate;
import com.example.participationservice.service.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CertificateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CertificateService certificateService;

    @InjectMocks
    private CertificateController certificateController;

    private Certificate certificate;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(certificateController).build();

        certificate = new Certificate();
        certificate.setCertificateId(1L);
        certificate.setIssueDate(new Date());
        certificate.setCertificateStatus("Issued");
    }

    @Test
    public void testGetAllCertificates() throws Exception {
        when(certificateService.getAllCertificates()).thenReturn(Arrays.asList(certificate));

        mockMvc.perform(get("/certificates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].certificateId", is(1)))
                .andExpect(jsonPath("$[0].certificateStatus", is("Issued")));
    }

    @Test
    public void testGetCertificateById_Found() throws Exception {
        when(certificateService.getCertificateById(1L)).thenReturn(Optional.of(certificate));

        mockMvc.perform(get("/certificates/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.certificateId", is(1)))
                .andExpect(jsonPath("$.certificateStatus", is("Issued")));
    }

    @Test
    public void testGetCertificateById_NotFound() throws Exception {
        when(certificateService.getCertificateById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/certificates/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCertificate() throws Exception {
    when(certificateService.createCertificate(any(Certificate.class))).thenReturn(certificate);

    mockMvc.perform(post("/certificates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"certificateId\":1, \"certificateStatus\":\"Issued\", \"issueDate\":\"2024-03-26T12:00:00.000+00:00\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.certificateId", is(1)))
            .andExpect(jsonPath("$.certificateStatus", is("Issued")));
    }


    @Test
    public void testUpdateCertificate_Found() throws Exception {
        Certificate updatedCertificate = new Certificate();
        updatedCertificate.setCertificateId(1L);
        updatedCertificate.setIssueDate(new Date());
        updatedCertificate.setCertificateStatus("Updated");

        when(certificateService.updateCertificate(any(Long.class), any(Certificate.class)))
                .thenReturn(updatedCertificate);

                mockMvc.perform(put("/certificates/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"certificateId\":1, \"certificateStatus\":\"Updated\", \"issueDate\":\"2024-03-26T12:00:00.000+00:00\"}"))
        .andExpect(status().isOk()) 
        .andExpect(jsonPath("$.certificateId", is(1)))
        .andExpect(jsonPath("$.certificateStatus", is("Updated")));

    }

    @Test
    public void testUpdateCertificate_NotFound() throws Exception {
        String jsonRequest = """
            {
                "certificateId": 1,
                "certificateStatus": "Updated",
                "issueDate": "2024-03-26T12:00:00.000+00:00"
            }
        """;
    
        when(certificateService.updateCertificate(any(Long.class), any(Certificate.class)))
                .thenReturn(null);
    
        mockMvc.perform(put("/certificates/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound()); 
    }
    

    @Test
    public void testDeleteCertificate_Success() throws Exception {
        when(certificateService.deleteCertificate(1L)).thenReturn(true);

        mockMvc.perform(delete("/certificates/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCertificate_NotFound() throws Exception {
        when(certificateService.deleteCertificate(1L)).thenReturn(false);

        mockMvc.perform(delete("/certificates/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}
