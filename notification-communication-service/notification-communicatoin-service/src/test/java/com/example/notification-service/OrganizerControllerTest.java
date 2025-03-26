package com.example.notificationservice;

import com.example.notificationservice.controller.OrganizerController;
import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.service.OrganizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrganizerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrganizerService organizerService;

    @InjectMocks
    private OrganizerController organizerController;

    private Organizer organizer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();

        organizer = new Organizer();
        organizer.setOrganizerId(1L);
        organizer.setName("Test Organizer");
        organizer.setEmail("test@example.com");
        organizer.setPhoneNumber("123456789");
    }

    @Test
    void getAllOrganizers_ShouldReturnListOfOrganizers() throws Exception {
        when(organizerService.getAllOrganizers()).thenReturn(Arrays.asList(organizer));

        mockMvc.perform(get("/organizers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Organizer"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("123456789"));

        verify(organizerService, times(1)).getAllOrganizers();
    }

    @Test
    void getOrganizerById_ShouldReturnOrganizer() throws Exception {
        when(organizerService.getOrganizerById(1L)).thenReturn(Optional.of(organizer));

        mockMvc.perform(get("/organizers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Organizer"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"));

        verify(organizerService, times(1)).getOrganizerById(1L);
    }

    @Test
    void getOrganizerById_ShouldReturnNotFound_WhenOrganizerDoesNotExist() throws Exception {
        when(organizerService.getOrganizerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/organizers/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(organizerService, times(1)).getOrganizerById(1L);
    }

    @Test
    void createOrganizer_ShouldReturnCreatedOrganizer() throws Exception {
        when(organizerService.createOrganizer(any(Organizer.class))).thenReturn(organizer);

        mockMvc.perform(post("/organizers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Organizer\", \"email\":\"test@example.com\", \"phoneNumber\":\"123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Organizer"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"));

        verify(organizerService, times(1)).createOrganizer(any(Organizer.class));
    }

    @Test
    void updateOrganizer_ShouldReturnUpdatedOrganizer() throws Exception {
        when(organizerService.updateOrganizer(eq(1L), any(Organizer.class))).thenReturn(organizer);

        mockMvc.perform(put("/organizers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Organizer\", \"email\":\"updated@example.com\", \"phoneNumber\":\"987654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Organizer"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"));

        verify(organizerService, times(1)).updateOrganizer(eq(1L), any(Organizer.class));
    }

    @Test
    void updateOrganizer_ShouldReturnNotFound_WhenOrganizerDoesNotExist() throws Exception {
        when(organizerService.updateOrganizer(eq(1L), any(Organizer.class))).thenThrow(new RuntimeException("Organizer not found"));

        mockMvc.perform(put("/organizers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Organizer\", \"email\":\"updated@example.com\", \"phoneNumber\":\"987654321\"}"))
                .andExpect(status().isNotFound());

        verify(organizerService, times(1)).updateOrganizer(eq(1L), any(Organizer.class));
    }

    @Test
    void deleteOrganizer_ShouldReturnNoContent() throws Exception {
        doNothing().when(organizerService).deleteOrganizer(1L);

        mockMvc.perform(delete("/organizers/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(organizerService, times(1)).deleteOrganizer(1L);
    }

    @Test
void deleteOrganizer_ShouldReturnNotFound_WhenOrganizerDoesNotExist() throws Exception {
    doThrow(new EntityNotFoundException("Organizer not found")).when(organizerService).deleteOrganizer(1L);

    mockMvc.perform(delete("/organizers/{id}", 1L))
            .andExpect(status().isNotFound()); 

    verify(organizerService, times(1)).deleteOrganizer(1L);
}

}
