package com.example.notificationservice;

import com.example.notificationservice.service.OrganizerService;
import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.repository.OrganizerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import javax.persistence.EntityNotFoundException;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class OrganizerServiceTest {

    @Mock
    private OrganizerRepository organizerRepository;

    @InjectMocks
    private OrganizerService organizerService;

    private Organizer organizer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        organizer = new Organizer();
        organizer.setOrganizerId(1L);
        organizer.setName("Test Organizer");
        organizer.setEmail("test@example.com");
        organizer.setPhoneNumber("123456789");
    }

    @Test
    void getAllOrganizers_ShouldReturnOrganizers() {
        Mockito.when(organizerRepository.findAll()).thenReturn(List.of(organizer));

        var organizers = organizerService.getAllOrganizers();
        assertNotNull(organizers);
        assertEquals(1, organizers.size());
        assertEquals("Test Organizer", organizers.get(0).getName());
    }

    @Test
    void getOrganizerById_ShouldReturnOrganizer_WhenOrganizerExists() {
        Mockito.when(organizerRepository.findById(anyLong())).thenReturn(Optional.of(organizer));

        var result = organizerService.getOrganizerById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Organizer", result.get().getName());
    }

    @Test
    void getOrganizerById_ShouldReturnEmpty_WhenOrganizerDoesNotExist() {
        Mockito.when(organizerRepository.findById(anyLong())).thenReturn(Optional.empty());

        var result = organizerService.getOrganizerById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void createOrganizer_ShouldReturnCreatedOrganizer() {
        Mockito.when(organizerRepository.save(any(Organizer.class))).thenReturn(organizer);

        var createdOrganizer = organizerService.createOrganizer(organizer);
        assertNotNull(createdOrganizer);
        assertEquals("Test Organizer", createdOrganizer.getName());
    }

    @Test
    void updateOrganizer_ShouldReturnUpdatedOrganizer_WhenOrganizerExists() {
        var updatedOrganizer = new Organizer();
        updatedOrganizer.setName("Updated Organizer");
        updatedOrganizer.setEmail("updated@example.com");
        updatedOrganizer.setPhoneNumber("987654321");

        Mockito.when(organizerRepository.findById(anyLong())).thenReturn(Optional.of(organizer));
        Mockito.when(organizerRepository.save(any(Organizer.class))).thenReturn(updatedOrganizer);

        var result = organizerService.updateOrganizer(1L, updatedOrganizer);
        assertNotNull(result);
        assertEquals("Updated Organizer", result.getName());
    }

    @Test
    void updateOrganizer_ShouldThrowException_WhenOrganizerDoesNotExist() {
        var updatedOrganizer = new Organizer();
        updatedOrganizer.setName("Updated Organizer");

        Mockito.when(organizerRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            organizerService.updateOrganizer(1L, updatedOrganizer);
        });

        assertEquals("Organizer not found", exception.getMessage());
    }

    @Test
    void saveOrganizer_ShouldSaveOrganizer() {
        Mockito.when(organizerRepository.save(any(Organizer.class))).thenReturn(organizer);

        organizerService.saveOrganizer(organizer);

        Mockito.verify(organizerRepository).save(organizer);
    }

    @Test
    void deleteOrganizer_ShouldThrowException_WhenOrganizerDoesNotExist() {
        Mockito.when(organizerRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            organizerService.deleteOrganizer(1L);
        });

        assertEquals("Organizer not found with ID 1", exception.getMessage());
    }

    @Test
    void deleteOrganizer_ShouldDeleteOrganizer_WhenOrganizerExists() {
        Mockito.when(organizerRepository.findById(anyLong())).thenReturn(Optional.of(organizer));

        organizerService.deleteOrganizer(1L);

        Mockito.verify(organizerRepository).deleteById(1L);
    }
}
