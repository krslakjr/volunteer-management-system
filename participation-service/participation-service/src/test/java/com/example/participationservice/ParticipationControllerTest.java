package com.example.participationservice.controller;

import com.example.participationservice.exception.ResourceNotFoundException;
import com.example.participationservice.models.Participation;
import com.example.participationservice.service.ParticipationService;
import com.example.participationservice.exception.*;

import com.example.participationservice.controller.ParticipationController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ParticipationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParticipationService participationService;

    @InjectMocks
    private ParticipationController participationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(participationController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void getAllParticipations() throws Exception {
        mockMvc.perform(get("/participations"))
                .andExpect(status().isOk());
    }

    @Test
    void getParticipationById_WhenParticipationExists() throws Exception {
        Participation participation = new Participation();
        participation.setParticipationId(1L);
        participation.setAttendanceStatus("Attended");

        when(participationService.getParticipationById(1L)).thenReturn(Optional.of(participation));

        mockMvc.perform(get("/participations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendanceStatus").value("Attended"));
    }

    @Test
    void getParticipationById_WhenParticipationNotFound() throws Exception {
        when(participationService.getParticipationById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/participations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createParticipation() throws Exception {
        Participation participation = new Participation();
        participation.setAttendanceStatus("Attended");
        participation.setRegistrationDate(new java.util.Date());

        when(participationService.createParticipation(any(Participation.class))).thenReturn(participation);

        mockMvc.perform(post("/participations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"attendanceStatus\": \"Attended\", \"registrationDate\": \"2025-04-02\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendanceStatus").value("Attended"));
    }

    @Test
    void updateParticipation_WhenParticipationExists() throws Exception {
        Participation participation = new Participation();
        participation.setParticipationId(1L);
        participation.setAttendanceStatus("Attended");
        
        Participation updatedParticipation = new Participation();
        updatedParticipation.setParticipationId(1L);
        updatedParticipation.setAttendanceStatus("Updated Status");

        when(participationService.updateParticipation(eq(1L), any(Participation.class))).thenReturn(updatedParticipation);

        mockMvc.perform(put("/participations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"attendanceStatus\": \"Updated Status\", \"registrationDate\": \"2025-04-02\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendanceStatus").value("Updated Status"));
    }

    @Test
    void updateParticipation_WhenParticipationNotFound() throws Exception {
        when(participationService.updateParticipation(eq(1L), any(Participation.class))).thenThrow(new ResourceNotFoundException("Participation not found with id 1", "id"));

        mockMvc.perform(put("/participations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"attendanceStatus\": \"Updated Status\", \"registrationDate\": \"2025-04-02\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteParticipation_WhenParticipationExists() throws Exception {
        when(participationService.deleteParticipation(1L)).thenReturn(true);

        mockMvc.perform(delete("/participations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteParticipation_WhenParticipationNotFound() throws Exception {
        when(participationService.deleteParticipation(1L)).thenReturn(false);

        mockMvc.perform(delete("/participations/1"))
                .andExpect(status().isNotFound());
    }
}
