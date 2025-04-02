package com.example.participationservice;

import com.example.participationservice.service.ParticipationService;
import com.example.participationservice.models.Participation;
import com.example.participationservice.repository.ParticipationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.participationservice.exception.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @InjectMocks
    private ParticipationService participationService;

    private Participation participation;

    @BeforeEach
    void setUp() {
        participation = new Participation();
        participation.setParticipationId(1L);
        participation.setAttendanceStatus("Present");
    }

    @Test
    void testGetAllParticipations() {
        List<Participation> participations = Arrays.asList(participation);
        when(participationRepository.findAll()).thenReturn(participations);

        List<Participation> result = participationService.getAllParticipations();

        assertEquals(1, result.size());
        verify(participationRepository, times(1)).findAll();
    }

    @Test
    void testSaveParticipation() {
        participationService.saveParticipation(participation);
        verify(participationRepository, times(1)).save(participation);
    }

    @Test
    void testGetParticipationById_Found() {
        when(participationRepository.findById(1L)).thenReturn(Optional.of(participation));

        Optional<Participation> result = participationService.getParticipationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getParticipationId());
        verify(participationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetParticipationById_NotFound() {
        when(participationRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Participation> result = participationService.getParticipationById(1L);

        assertFalse(result.isPresent());
        verify(participationRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateParticipation() {
        when(participationRepository.save(participation)).thenReturn(participation);

        Participation result = participationService.createParticipation(participation);

        assertNotNull(result);
        assertEquals(1L, result.getParticipationId());
        verify(participationRepository, times(1)).save(participation);
    }
 
    @Test
    void testUpdateParticipation_Found() {
        Participation updatedParticipation = new Participation();
        updatedParticipation.setAttendanceStatus("Absent");

        when(participationRepository.findById(1L)).thenReturn(Optional.of(participation));
        when(participationRepository.save(any(Participation.class))).thenReturn(updatedParticipation);

        Participation result = participationService.updateParticipation(1L, updatedParticipation);

        assertNotNull(result);
        assertEquals("Absent", result.getAttendanceStatus());
        verify(participationRepository, times(1)).findById(1L);
        verify(participationRepository, times(1)).save(participation);
    }

    @Test
void testUpdateParticipation_NotFound() {
    Participation updatedParticipation = new Participation();
    when(participationRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> {
        participationService.updateParticipation(1L, updatedParticipation);
    });

    verify(participationRepository, times(1)).findById(1L);
    verify(participationRepository, times(0)).save(any(Participation.class)); 
}


    @Test
    void testDeleteParticipation_Found() {
        when(participationRepository.existsById(1L)).thenReturn(true);

        boolean result = participationService.deleteParticipation(1L);

        assertTrue(result);
        verify(participationRepository, times(1)).existsById(1L);
        verify(participationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteParticipation_NotFound() {
        when(participationRepository.existsById(1L)).thenReturn(false);

        boolean result = participationService.deleteParticipation(1L);

        assertFalse(result);
        verify(participationRepository, times(1)).existsById(1L);
        verify(participationRepository, times(0)).deleteById(anyLong());
    }
}
