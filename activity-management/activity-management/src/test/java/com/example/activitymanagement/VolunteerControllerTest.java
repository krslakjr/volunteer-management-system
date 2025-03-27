package com.example.activitymanagement;

import com.example.activitymanagement.controller.VolunteerController;
import com.example.activitymanagement.models.Volunteer;
import com.example.activitymanagement.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerControllerTest {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    private Volunteer volunteer;

    @BeforeEach
    void setUp() {
        volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("John Doe");
        volunteer.setContactInfo("johndoe@example.com");
    }

    @Test
    void testGetAllVolunteers() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(volunteer));

        List<Volunteer> result = volunteerController.getAllVolunteers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(volunteerService, times(1)).getAllVolunteers();
    }

    @Test
    void testGetVolunteerById_Found() {
        when(volunteerService.getVolunteerById(1L)).thenReturn(Optional.of(volunteer));

        ResponseEntity<Volunteer> result = volunteerController.getVolunteerById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("John Doe", result.getBody().getName());
        verify(volunteerService, times(1)).getVolunteerById(1L);
    }

    @Test
    void testGetVolunteerById_NotFound() {
        when(volunteerService.getVolunteerById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Volunteer> result = volunteerController.getVolunteerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(volunteerService, times(1)).getVolunteerById(1L);
    }

    @Test
    void testCreateVolunteer() {
        when(volunteerService.createVolunteer(any(Volunteer.class))).thenReturn(volunteer);

        Volunteer result = volunteerController.createVolunteer(volunteer);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(volunteerService, times(1)).createVolunteer(any(Volunteer.class));
    }

    @Test
    void testUpdateVolunteer() {
        when(volunteerService.updateVolunteer(anyLong(), any(Volunteer.class))).thenReturn(volunteer);

        ResponseEntity<Volunteer> result = volunteerController.updateVolunteer(1L, volunteer);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("John Doe", result.getBody().getName());
        verify(volunteerService, times(1)).updateVolunteer(anyLong(), any(Volunteer.class));
    }

    @Test
    void testDeleteVolunteer_Success() {
        doNothing().when(volunteerService).deleteVolunteer(1L);

        ResponseEntity<Void> result = volunteerController.deleteVolunteer(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(volunteerService, times(1)).deleteVolunteer(1L);
    }
}
