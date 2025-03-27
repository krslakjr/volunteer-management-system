package com.example.activitymanagement;

import com.example.activitymanagement.service.ActivityService;
import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.mapper.ActivityMapper;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity;
    private ActivityDTO activityDTO;

    @BeforeEach
    void setUp() {
        activity = new Activity();
        activity.setActivityId(1L);
        activity.setDescription("Community Cleanup");
        activity.setDate("2025-03-28");
        activity.setLocation("City Park");
        activity.setVolunteersNeeded(10);

        activityDTO = new ActivityDTO(1L, "Community Cleanup", "2025-03-28", "City Park", 10);
    }

    @Test
    void testGetAllActivities() {
        when(activityRepository.findAll()).thenReturn(List.of(activity));
        when(activityMapper.toActivityDTO(any(Activity.class))).thenReturn(activityDTO);

        List<ActivityDTO> result = activityService.getAllActivities();
        assertEquals(1, result.size());
        assertEquals(activityDTO.getActivityId(), result.get(0).getActivityId());
        assertEquals("Community Cleanup", result.get(0).getDescription());

        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void testGetActivityById_Found() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityMapper.toActivityDTO(any(Activity.class))).thenReturn(activityDTO);

        Optional<ActivityDTO> result = activityService.getActivityById(1L);
        assertTrue(result.isPresent());
        assertEquals(activityDTO.getActivityId(), result.get().getActivityId());

        verify(activityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetActivityById_NotFound() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ActivityDTO> result = activityService.getActivityById(1L);
        assertFalse(result.isPresent());

        verify(activityRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateActivity() {
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);
        when(activityMapper.toActivityDTO(any(Activity.class))).thenReturn(activityDTO);

        ActivityDTO result = activityService.createActivity(activity);
        assertNotNull(result);
        assertEquals(activityDTO.getActivityId(), result.getActivityId());
        assertEquals("Community Cleanup", result.getDescription());

        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void testUpdateActivity_Found() {
        Activity updatedActivity = new Activity();
        updatedActivity.setDescription("Updated Cleanup");
        updatedActivity.setDate("2025-03-29");
        updatedActivity.setLocation("Downtown Park");
        updatedActivity.setVolunteersNeeded(15);

        ActivityDTO updatedActivityDTO = new ActivityDTO(1L, "Updated Cleanup", "2025-03-29", "Downtown Park", 15);

        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityRepository.save(any(Activity.class))).thenReturn(updatedActivity);
        when(activityMapper.toActivityDTO(any(Activity.class))).thenReturn(updatedActivityDTO);

        ActivityDTO result = activityService.updateActivity(1L, updatedActivity);
        assertEquals("Updated Cleanup", result.getDescription());
        assertEquals("2025-03-29", result.getDate());
        assertEquals("Downtown Park", result.getLocation());
        assertEquals(15, result.getVolunteersNeeded());

        verify(activityRepository, times(1)).findById(1L);
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void testUpdateActivity_NotFound() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            activityService.updateActivity(1L, activity);
        });

        assertEquals("Activity not found", exception.getMessage());
        verify(activityRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteActivity() {
        doNothing().when(activityRepository).deleteById(1L);

        activityService.deleteActivity(1L);

        verify(activityRepository, times(1)).deleteById(1L);
    }
}
