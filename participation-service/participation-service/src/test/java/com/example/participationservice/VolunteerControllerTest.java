package com.example.participationservice;

import com.example.participationservice.controller.VolunteerController;
import com.example.participationservice.models.Volunteer;
import com.example.participationservice.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VolunteerControllerTest {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    private Volunteer volunteer;

    @BeforeEach
    public void setUp() {
        volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("John Doe");
        volunteer.setContactInfo("john.doe@example.com");
    }

    @Test
    public void testGetAllVolunteers() {
        when(volunteerService.getAllVolunteers()).thenReturn(Arrays.asList(volunteer));

        var response = volunteerController.getAllVolunteers();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("John Doe", response.get(0).getName());
    }

    @Test
    public void testGetVolunteerById_Found() {
        when(volunteerService.getVolunteerById(1L)).thenReturn(Optional.of(volunteer));

        ResponseEntity<Volunteer> response = volunteerController.getVolunteerById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testGetVolunteerById_NotFound() {
        when(volunteerService.getVolunteerById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Volunteer> response = volunteerController.getVolunteerById(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateVolunteer() {
        when(volunteerService.createVolunteer(any(Volunteer.class))).thenReturn(volunteer);

        ResponseEntity<Volunteer> response = volunteerController.createVolunteer(volunteer);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("John Doe", response.getBody().getName());
        verify(volunteerService, times(1)).createVolunteer(any(Volunteer.class));
    }

    @Test
    public void testUpdateVolunteer_Found() {
        when(volunteerService.updateVolunteer(eq(1L), any(Volunteer.class))).thenReturn(volunteer);

        ResponseEntity<Volunteer> response = volunteerController.updateVolunteer(1L, volunteer);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testUpdateVolunteer_NotFound() {
        when(volunteerService.updateVolunteer(eq(1L), any(Volunteer.class))).thenReturn(null);

        ResponseEntity<Volunteer> response = volunteerController.updateVolunteer(1L, volunteer);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void testDeleteVolunteer_Success() {
        when(volunteerService.deleteVolunteer(1L)).thenReturn(true);

        ResponseEntity<Void> response = volunteerController.deleteVolunteer(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(volunteerService, times(1)).deleteVolunteer(1L);
    }

    @Test
    public void testDeleteVolunteer_NotFound() {
        when(volunteerService.deleteVolunteer(1L)).thenReturn(false);

        ResponseEntity<Void> response = volunteerController.deleteVolunteer(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
    }
}
