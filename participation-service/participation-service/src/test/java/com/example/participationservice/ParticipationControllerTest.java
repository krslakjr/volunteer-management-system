package com.example.participationservice.controller;

import com.example.participationservice.exception.ResourceNotFoundException;
import com.example.participationservice.models.Activity;
import com.example.participationservice.models.Participation;
import com.example.participationservice.models.Volunteer;
import com.example.participationservice.service.ActivityClientService;
import com.example.participationservice.service.ParticipationService;
import com.example.participationservice.service.UserClientService;
import com.example.participationservice.exception.GlobalExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ParticipationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParticipationService participationService;
    @Mock
    private UserClientService userClientService;
    @Mock
    private ActivityClientService activityClientService;

    @InjectMocks
    private ParticipationController participationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(participationController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void getAllParticipations() throws Exception {
        Participation p1 = new Participation();
        p1.setParticipationId(1L);
        p1.setAttendanceStatus("Attended");

        Participation p2 = new Participation();
        p2.setParticipationId(2L);
        p2.setAttendanceStatus("Pending");

        List<Participation> participations = Arrays.asList(p1, p2);
        when(participationService.getAllParticipations()).thenReturn(participations);

        mockMvc.perform(get("/participations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].attendanceStatus").value("Attended"))
                .andExpect(jsonPath("$[1].attendanceStatus").value("Pending"));
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
        Participation createdParticipation = new Participation();
        createdParticipation.setParticipationId(1L);
        createdParticipation.setAttendanceStatus("Attended");
        createdParticipation.setRegistrationDate(new Date());

        when(userClientService.isValidVolunteer(anyLong())).thenReturn(true);
        when(activityClientService.doesActivityExist(anyLong())).thenReturn(true);
        doNothing().when(activityClientService).decreaseActivitySlot(anyLong());
        when(participationService.createParticipation(any(Participation.class))).thenReturn(createdParticipation);

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("attendanceStatus", "Attended");
        requestBody.put("registrationDate", new Date().getTime());
        ObjectNode volunteerNode = objectMapper.createObjectNode();
        volunteerNode.put("volunteerId", 1L);
        requestBody.set("volunteer", volunteerNode);
        ObjectNode activityNode = objectMapper.createObjectNode();
        activityNode.put("activityId", 101L);
        requestBody.set("activity", activityNode);

        String jsonParticipation = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post("/participations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParticipation))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        verify(userClientService, times(1)).isValidVolunteer(1L);
        verify(activityClientService, times(1)).doesActivityExist(101L);
        verify(activityClientService, times(1)).decreaseActivitySlot(101L);
        verify(participationService, times(1)).createParticipation(any(Participation.class));
    }


    @Test
    void updateParticipation_WhenParticipationExists() throws Exception {
        Participation participation = new Participation();
        participation.setParticipationId(1L);
        participation.setAttendanceStatus("Attended");
        participation.setRegistrationDate(new Date());

        Participation updatedParticipation = new Participation();
        updatedParticipation.setParticipationId(1L);
        updatedParticipation.setAttendanceStatus("Updated Status");
        updatedParticipation.setRegistrationDate(new Date());

        when(participationService.updateParticipation(eq(1L), any(Participation.class))).thenReturn(updatedParticipation);

        String jsonContent = objectMapper.writeValueAsString(updatedParticipation);

        mockMvc.perform(put("/participations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendanceStatus").value("Updated Status"));
    }

    @Test
    void updateParticipation_WhenParticipationNotFound() throws Exception {
        Participation participationDetails = new Participation();
        participationDetails.setAttendanceStatus("Updated Status");
        participationDetails.setRegistrationDate(new Date());

        String jsonContent = objectMapper.writeValueAsString(participationDetails); 

        when(participationService.updateParticipation(eq(1L), any(Participation.class)))
                .thenThrow(new ResourceNotFoundException("Participation not found with id 1", "id"));


        mockMvc.perform(put("/participations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
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