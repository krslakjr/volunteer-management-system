package com.example.activitymanagement;

import com.example.activitymanagement.service.VolunteerService;
import com.example.activitymanagement.models.Volunteer;
import com.example.activitymanagement.repository.VolunteerRepository;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;

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
        when(volunteerRepository.findAll()).thenReturn(List.of(volunteer));

        List<Volunteer> result = volunteerService.getAllVolunteers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(volunteerRepository, times(1)).findAll();
    }

    @Test
    void testGetVolunteerById_Found() {
        when(volunteerRepository.findById(1L)).thenReturn(java.util.Optional.of(volunteer));

        Volunteer result = volunteerService.getVolunteerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getVolunteerId());
        assertEquals("John Doe", result.getName());
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVolunteerById_NotFound() {
        when(volunteerRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> volunteerService.getVolunteerById(1L));

        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateVolunteer() {
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        Volunteer result = volunteerService.createVolunteer(volunteer);

        assertNotNull(result);
        assertEquals(1L, result.getVolunteerId());
        assertEquals("John Doe", result.getName());
        verify(volunteerRepository, times(1)).save(any(Volunteer.class));
    }

    @Test
    void testUpdateVolunteer_Found() {
        when(volunteerRepository.findById(1L)).thenReturn(java.util.Optional.of(volunteer));
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        Volunteer result = volunteerService.updateVolunteer(1L, volunteer);

        assertNotNull(result);
        assertEquals(1L, result.getVolunteerId());
        assertEquals("John Doe", result.getName());
        verify(volunteerRepository, times(1)).findById(1L);
        verify(volunteerRepository, times(1)).save(any(Volunteer.class));
    }

    @Test
    void testUpdateVolunteer_NotFound() {
        when(volunteerRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> volunteerService.updateVolunteer(1L, volunteer));

        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteVolunteer_Success() {
        when(volunteerRepository.findById(1L)).thenReturn(java.util.Optional.of(volunteer));

        volunteerService.deleteVolunteer(1L);

        verify(volunteerRepository, times(1)).findById(1L);
        verify(volunteerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVolunteer_NotFound() {
        when(volunteerRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> volunteerService.deleteVolunteer(1L));

        verify(volunteerRepository, times(1)).findById(1L);
    }
}
