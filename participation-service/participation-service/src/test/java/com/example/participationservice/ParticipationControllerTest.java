package com.example.participationservice;

import com.example.participationservice.controller.ParticipationController;
import com.example.participationservice.models.Participation;
import com.example.participationservice.service.ParticipationService;

import com.example.participationservice.exception.GlobalExceptionHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import java.util.Date;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParticipationController.class)
@ExtendWith(MockitoExtension.class)
public class ParticipationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipationService participationService;

    @InjectMocks
    private ParticipationController participationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(participationController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();

    }

    @Test
    void testGetAllParticipations() throws Exception {
        when(participationService.getAllParticipations()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/participations"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(participationService, times(1)).getAllParticipations();
    }

    @Test
    void testGetParticipationById_Found() throws Exception {
        Participation participation = new Participation();
        participation.setParticipationId(1L);

        when(participationService.getParticipationById(1L)).thenReturn(Optional.of(participation));

        mockMvc.perform(get("/participations/1"))
                .andExpect(status().isOk());

        verify(participationService, times(1)).getParticipationById(1L);
    }

    @Test
    void testGetParticipationById_NotFound() throws Exception {
        when(participationService.getParticipationById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/participations/1"))
                .andExpect(status().isNotFound());

        verify(participationService, times(1)).getParticipationById(1L);
    }

    @Test
void testCreateParticipation() throws Exception {
    String jsonRequest = """
        {
            "registrationDate": "2024-03-26T12:00:00.000+00:00",
            "attendanceStatus": "Present"
        }
    """;

    Participation participation = new Participation();
    participation.setParticipationId(1L);
    participation.setRegistrationDate(new Date());
    participation.setAttendanceStatus("Present");

    when(participationService.createParticipation(any(Participation.class))).thenReturn(participation);

    mockMvc.perform(post("/participations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isOk());

    verify(participationService, times(1)).createParticipation(any(Participation.class));
}


@Test
void testUpdateParticipation_Found() throws Exception {
    String jsonRequest = """
        {
            "registrationDate": "2024-03-26T12:00:00.000+00:00",
            "attendanceStatus": "Present"
        }
    """;

    Participation participation = new Participation();
    participation.setParticipationId(1L);
    participation.setRegistrationDate(new Date());
    participation.setAttendanceStatus("Present");

    when(participationService.updateParticipation(eq(1L), any(Participation.class))).thenReturn(participation);

    mockMvc.perform(put("/participations/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isOk());

    verify(participationService, times(1)).updateParticipation(eq(1L), any(Participation.class));
}

@Test
void testUpdateParticipation_NotFound() throws Exception {
    String jsonRequest = """
        {
            "registrationDate": "2024-03-26T12:00:00.000+00:00",
            "attendanceStatus": "Absent"
        }
    """;

    when(participationService.updateParticipation(eq(1L), any(Participation.class))).thenReturn(null);

    mockMvc.perform(put("/participations/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andExpect(status().isNotFound());

    verify(participationService, times(1)).updateParticipation(eq(1L), any(Participation.class));
}

    @Test
    void testDeleteParticipation_Found() throws Exception {
        when(participationService.deleteParticipation(1L)).thenReturn(true);

        mockMvc.perform(delete("/participations/1"))
                .andExpect(status().isNoContent());

        verify(participationService, times(1)).deleteParticipation(1L);
    }

    @Test
    void testDeleteParticipation_NotFound() throws Exception {
        when(participationService.deleteParticipation(1L)).thenReturn(false);

        mockMvc.perform(delete("/participations/1"))
                .andExpect(status().isNotFound());

        verify(participationService, times(1)).deleteParticipation(1L);
    }
}
