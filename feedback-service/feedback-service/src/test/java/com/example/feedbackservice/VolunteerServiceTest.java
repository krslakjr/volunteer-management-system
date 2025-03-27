package com.example.feedbackservice;

import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.repository.VolunteerRepository;
import com.example.feedbackservice.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVolunteers() {
        // Arrange
        Volunteer v1 = new Volunteer();
        v1.setVolunteerId(1L);
        v1.setName("John Doe");

        Volunteer v2 = new Volunteer();
        v2.setVolunteerId(2L);
        v2.setName("Jane Doe");

        List<Volunteer> volunteers = Arrays.asList(v1, v2);
        when(volunteerRepository.findAll()).thenReturn(volunteers);

        // Act
        List<Volunteer> result = volunteerService.getAllVolunteers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        verify(volunteerRepository, times(1)).findAll();
    }

    @Test
    void testGetVolunteerById_Found() {
        // Arrange
        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("John Doe");

        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));

        // Act
        Optional<Volunteer> result = volunteerService.getVolunteerById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVolunteerById_NotFound() {
        // Arrange
        when(volunteerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Volunteer> result = volunteerService.getVolunteerById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveOrUpdateVolunteer() {
        // Arrange
        Volunteer volunteer = new Volunteer();
        volunteer.setName("John Doe");

        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Act
        Volunteer savedVolunteer = volunteerService.saveOrUpdateVolunteer(volunteer);

        // Assert
        assertNotNull(savedVolunteer);
        assertEquals("John Doe", savedVolunteer.getName());
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    void testDeleteVolunteer_Success() {
        // Arrange
        when(volunteerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(volunteerRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> volunteerService.deleteVolunteer(1L));

        // Assert
        verify(volunteerRepository, times(1)).existsById(1L);
        verify(volunteerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVolunteer_NotFound() {
        // Arrange
        when(volunteerRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            volunteerService.deleteVolunteer(1L);
        });

        assertEquals("Volunteer not found with id 1", exception.getMessage());
        verify(volunteerRepository, times(1)).existsById(1L);
        verify(volunteerRepository, never()).deleteById(1L);
    }
}
