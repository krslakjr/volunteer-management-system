package com.example.feedbackservice;

import com.example.feedbackservice.controller.VolunteerController;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VolunteerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(volunteerController).build();
    }

    @Test
    void testGetAllVolunteers() throws Exception {
        // Arrange
        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("John Doe");
        volunteer.setContactInfo("john.doe@example.com");

        List<Volunteer> volunteers = new ArrayList<>();
        volunteers.add(volunteer);

        when(volunteerService.getAllVolunteers()).thenReturn(volunteers);

        // Act & Assert
        mockMvc.perform(get("/volunteers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].volunteerId").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testGetVolunteerById_Found() throws Exception {
        // Arrange
        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("John Doe");
        volunteer.setContactInfo("john.doe@example.com");

        when(volunteerService.getVolunteerById(1L)).thenReturn(Optional.of(volunteer));

        // Act & Assert
        mockMvc.perform(get("/volunteers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.volunteerId").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetVolunteerById_NotFound() throws Exception {
        // Arrange
        when(volunteerService.getVolunteerById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/volunteers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrUpdateVolunteer() throws Exception {
        // Arrange
        Volunteer volunteer = new Volunteer();
        volunteer.setName("John Doe");
        volunteer.setContactInfo("john.doe@example.com");

        when(volunteerService.saveOrUpdateVolunteer(any(Volunteer.class)))
                .thenReturn(volunteer);

        // Act & Assert
        mockMvc.perform(post("/volunteers")
                        .contentType("application/json")
                        .content("{ \"name\": \"John Doe\", \"contactInfo\": \"john.doe@example.com\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.contactInfo").value("john.doe@example.com"));
    }

    @Test
    void testDeleteVolunteer() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/volunteers/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verify that delete method was called
        verify(volunteerService, times(1)).deleteVolunteer(1L);
    }
}
