package com.example.notificationservice;

import com.example.notificationservice.controller.VolunteerController;
import com.example.notificationservice.models.Volunteer;
import com.example.notificationservice.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import com.example.notificationservice.exception.*;
import java.util.Optional;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VolunteerControllerTest {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    private MockMvc mockMvc;
    private Volunteer volunteer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
mockMvc = MockMvcBuilders.standaloneSetup(volunteerController)
.setControllerAdvice(new GlobalExceptionHandler())
.build();

        volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("Test Volunteer");
        volunteer.setEmail("volunteer@example.com");
        volunteer.setPhoneNumber("987654321");
    }


    @Test
    void getVolunteerById_ShouldReturnVolunteer_WhenExists() throws Exception {
        when(volunteerService.getVolunteerById(1L)).thenReturn(Optional.of(volunteer));

        mockMvc.perform(get("/volunteers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Volunteer"));
    }

    @Test
void getVolunteerById_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
    when(volunteerService.getVolunteerById(1L)).thenThrow(new VolunteerNotFoundException("Volunteer not found"));

    mockMvc.perform(get("/volunteers/{id}", 1L))
            .andExpect(status().isNotFound());  
}


    @Test
    void createVolunteer_ShouldReturnCreatedVolunteer() throws Exception {
        when(volunteerService.createVolunteer(any(Volunteer.class))).thenReturn(volunteer);

        mockMvc.perform(post("/volunteers")
                .contentType("application/json")
                .content("{\"name\": \"Test Volunteer\", \"email\": \"volunteer@example.com\", \"phoneNumber\": \"987654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Volunteer"));
    }


    void deleteVolunteer_ShouldReturnNoContent_WhenExists() throws Exception {
        doNothing().when(volunteerService).deleteVolunteer(1L);

        mockMvc.perform(delete("/volunteers/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
void deleteVolunteer_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
    doThrow(new VolunteerNotFoundException("Volunteer not found")).when(volunteerService).deleteVolunteer(1L);

    mockMvc.perform(delete("/volunteers/{id}", 1L))
            .andExpect(status().isNotFound()); 
}

}
