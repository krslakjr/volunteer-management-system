package com.example.notificationservice;

import com.example.notificationservice.models.Volunteer;
import com.example.notificationservice.repository.VolunteerRepository;
import com.example.notificationservice.service.VolunteerService;
import com.example.notificationservice.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;

    private Volunteer volunteer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("John Doe");
        volunteer.setEmail("johndoe@example.com");
        volunteer.setPhoneNumber("1234567890");
    }

    @Test
    public void testGetAllVolunteers() {
        List<Volunteer> volunteers = Arrays.asList(volunteer);
        when(volunteerRepository.findAll()).thenReturn(volunteers);

        List<Volunteer> result = volunteerService.getAllVolunteers();

        assertEquals(1, result.size());
        verify(volunteerRepository, times(1)).findAll();
    }

    @Test
    public void testGetVolunteerById() {
        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));

        Optional<Volunteer> result = volunteerService.getVolunteerById(1L);

        assertTrue(result.isPresent());
        assertEquals(volunteer.getVolunteerId(), result.get().getVolunteerId());
        verify(volunteerRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateVolunteer() {
        
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);

        Volunteer result = volunteerService.createVolunteer(volunteer);

        assertNotNull(result);
        assertEquals(volunteer.getVolunteerId(), result.getVolunteerId());
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    public void testUpdateVolunteer() {
        Volunteer updatedVolunteer = new Volunteer();
        updatedVolunteer.setName("Jane Doe");
        updatedVolunteer.setEmail("janedoe@example.com");
        updatedVolunteer.setPhoneNumber("0987654321");

        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);

        Volunteer result = volunteerService.updateVolunteer(1L, updatedVolunteer);
        assertEquals(updatedVolunteer.getName(), result.getName());
        assertEquals(updatedVolunteer.getEmail(), result.getEmail());
        assertEquals(updatedVolunteer.getPhoneNumber(), result.getPhoneNumber());
        verify(volunteerRepository, times(1)).findById(1L);
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
public void testUpdateVolunteerNotFound() {
    Volunteer updatedVolunteer = new Volunteer();
    updatedVolunteer.setName("Jane Doe");
    updatedVolunteer.setEmail("janedoe@example.com");
    updatedVolunteer.setPhoneNumber("0987654321");

    when(volunteerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(VolunteerNotFoundException.class, () -> volunteerService.updateVolunteer(1L, updatedVolunteer));
    verify(volunteerRepository, times(1)).findById(1L);
}


@Test
public void testDeleteVolunteer() {
    when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
    doNothing().when(volunteerRepository).deleteById(1L);

    volunteerService.deleteVolunteer(1L);

    verify(volunteerRepository, times(1)).deleteById(1L);
}


    @Test
public void testDeleteVolunteerNotFound() {
    when(volunteerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(VolunteerNotFoundException.class, () -> volunteerService.deleteVolunteer(1L));

    verify(volunteerRepository, times(1)).findById(1L);
    verify(volunteerRepository, never()).deleteById(anyLong());
}

}
