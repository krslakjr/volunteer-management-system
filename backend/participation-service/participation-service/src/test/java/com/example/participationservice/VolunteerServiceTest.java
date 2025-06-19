package com.example.participationservice;

import com.example.participationservice.service.VolunteerService;
import com.example.participationservice.models.Volunteer;
import com.example.participationservice.repository.VolunteerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        volunteer.setContactInfo("john.doe@example.com");
    }

    @Test
    void testGetAllVolunteers() {
        List<Volunteer> volunteers = Arrays.asList(volunteer);
        when(volunteerRepository.findAll()).thenReturn(volunteers);

        List<Volunteer> result = volunteerService.getAllVolunteers();

        assertEquals(1, result.size());
        verify(volunteerRepository, times(1)).findAll();
    }

    @Test
    void testSaveVolunteer() {
        volunteerService.saveVolunteer(volunteer);
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    void testGetVolunteerById_Found() {
        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));

        Optional<Volunteer> result = volunteerService.getVolunteerById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getVolunteerId());
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVolunteerById_NotFound() {
        when(volunteerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Volunteer> result = volunteerService.getVolunteerById(1L);

        assertFalse(result.isPresent());
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateVolunteer() {
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);

        Volunteer result = volunteerService.createVolunteer(volunteer);

        assertNotNull(result);
        assertEquals(1L, result.getVolunteerId());
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    void testUpdateVolunteer_Found() {
        Volunteer updatedVolunteer = new Volunteer();
        updatedVolunteer.setName("Jane Doe");
        updatedVolunteer.setContactInfo("jane.doe@example.com");

        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(updatedVolunteer);

        Volunteer result = volunteerService.updateVolunteer(1L, updatedVolunteer);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane.doe@example.com", result.getContactInfo());
        verify(volunteerRepository, times(1)).findById(1L);
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    void testUpdateVolunteer_NotFound() {
        Volunteer updatedVolunteer = new Volunteer();
        updatedVolunteer.setName("Jane Doe");
        updatedVolunteer.setContactInfo("jane.doe@example.com");

        when(volunteerRepository.findById(1L)).thenReturn(Optional.empty());

        Volunteer result = volunteerService.updateVolunteer(1L, updatedVolunteer);

        assertNull(result);
        verify(volunteerRepository, times(1)).findById(1L);
        verify(volunteerRepository, times(0)).save(any(Volunteer.class));
    }

    @Test
    void testDeleteVolunteer_Found() {
        when(volunteerRepository.existsById(1L)).thenReturn(true);

        boolean result = volunteerService.deleteVolunteer(1L);

        assertTrue(result);
        verify(volunteerRepository, times(1)).existsById(1L);
        verify(volunteerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVolunteer_NotFound() {
        when(volunteerRepository.existsById(1L)).thenReturn(false);

        boolean result = volunteerService.deleteVolunteer(1L);

        assertFalse(result);
        verify(volunteerRepository, times(1)).existsById(1L);
        verify(volunteerRepository, times(0)).deleteById(anyLong());
    }
}
