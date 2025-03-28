package com.example.activitymanagement;

import com.example.activitymanagement.service.ActivityVolunteerService;
import com.example.activitymanagement.models.Volunteer;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.dto.ActivityVolunteerDTO;
import com.example.activitymanagement.mapper.ActivityVolunteerMapper;
import com.example.activitymanagement.models.ActivityVolunteer;
import com.example.activitymanagement.repository.ActivityVolunteerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityVolunteerServiceTest {

    @Mock
    private ActivityVolunteerRepository activityVolunteerRepository;

    @Mock
    private ActivityVolunteerMapper activityVolunteerMapper;

    @InjectMocks
    private ActivityVolunteerService activityVolunteerService;

    private ActivityVolunteerDTO activityVolunteerDTO;
    private ActivityVolunteer activityVolunteer;

    @BeforeEach
    void setUp() {
        activityVolunteerDTO = new ActivityVolunteerDTO();
        activityVolunteerDTO.setId(1L);
        activityVolunteerDTO.setActivityId(1L);
        activityVolunteerDTO.setVolunteerId(1L);

        activityVolunteer = new ActivityVolunteer();
        activityVolunteer.setId(1L);
        activityVolunteer.setActivity(new Activity());
        activityVolunteer.setVolunteer(new Volunteer());
    }

    @Test
    void testGetAllActivityVolunteers() {
        when(activityVolunteerRepository.findAll()).thenReturn(List.of(activityVolunteer));
        when(activityVolunteerMapper.toDTO(any(ActivityVolunteer.class))).thenReturn(activityVolunteerDTO);

        var result = activityVolunteerService.getAllActivityVolunteers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(activityVolunteerRepository, times(1)).findAll();
    }

    @Test
    void testGetActivityVolunteerById_Found() {
        when(activityVolunteerRepository.findById(1L)).thenReturn(Optional.of(activityVolunteer));
        when(activityVolunteerMapper.toDTO(any(ActivityVolunteer.class))).thenReturn(activityVolunteerDTO);

        Optional<ActivityVolunteerDTO> result = activityVolunteerService.getActivityVolunteerById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(activityVolunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetActivityVolunteerById_NotFound() {
        when(activityVolunteerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ActivityVolunteerDTO> result = activityVolunteerService.getActivityVolunteerById(1L);

        assertFalse(result.isPresent());
        verify(activityVolunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateActivityVolunteer() {
        when(activityVolunteerMapper.toEntity(any(ActivityVolunteerDTO.class))).thenReturn(activityVolunteer);
        when(activityVolunteerRepository.save(any(ActivityVolunteer.class))).thenReturn(activityVolunteer);
        when(activityVolunteerMapper.toDTO(any(ActivityVolunteer.class))).thenReturn(activityVolunteerDTO);

        ActivityVolunteerDTO result = activityVolunteerService.createActivityVolunteer(activityVolunteerDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(activityVolunteerRepository, times(1)).save(any(ActivityVolunteer.class));
    }

    @Test
    void testCreateActivityVolunteer_InvalidData() {
        ActivityVolunteerDTO invalidDTO = new ActivityVolunteerDTO();
        invalidDTO.setActivityId(null); 
        invalidDTO.setVolunteerId(null);  

        assertThrows(IllegalArgumentException.class, () -> {
            activityVolunteerService.createActivityVolunteer(invalidDTO);
        });
    }

    @Test
    void testUpdateActivityVolunteer_Found() {
        when(activityVolunteerRepository.findById(1L)).thenReturn(Optional.of(activityVolunteer));
        when(activityVolunteerMapper.toEntity(any(ActivityVolunteerDTO.class))).thenReturn(activityVolunteer);
        when(activityVolunteerRepository.save(any(ActivityVolunteer.class))).thenReturn(activityVolunteer);
        when(activityVolunteerMapper.toDTO(any(ActivityVolunteer.class))).thenReturn(activityVolunteerDTO);

        Optional<ActivityVolunteerDTO> result = activityVolunteerService.updateActivityVolunteer(1L, activityVolunteerDTO);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(activityVolunteerRepository, times(1)).findById(1L);
        verify(activityVolunteerRepository, times(1)).save(any(ActivityVolunteer.class));
    }

    @Test
    void testUpdateActivityVolunteer_NotFound() {
        when(activityVolunteerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ActivityVolunteerDTO> result = activityVolunteerService.updateActivityVolunteer(1L, activityVolunteerDTO);

        assertFalse(result.isPresent());
        verify(activityVolunteerRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteActivityVolunteer_Success() {
        when(activityVolunteerRepository.findById(1L)).thenReturn(Optional.of(activityVolunteer));

        boolean result = activityVolunteerService.deleteActivityVolunteer(1L);

        assertTrue(result);
        verify(activityVolunteerRepository, times(1)).findById(1L);
        verify(activityVolunteerRepository, times(1)).delete(any(ActivityVolunteer.class));
    }

    @Test
    void testDeleteActivityVolunteer_NotFound() {
        when(activityVolunteerRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = activityVolunteerService.deleteActivityVolunteer(1L);

        assertFalse(result);
        verify(activityVolunteerRepository, times(1)).findById(1L);
    }
}
