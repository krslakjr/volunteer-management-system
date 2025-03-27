package com.example.feedbackservice;

import com.example.feedbackservice.service.ActivityService;
import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.repository.ActivityRepository;
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
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = new Activity();
        activity.setActivityId(1L);
        activity.setDescription("Community Cleanup");
        activity.setLocation("City Park");
        activity.setVolunteersNeeded(10);
    }

    @Test
    void testGetAllActivities() {
        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity));

        List<Activity> activities = activityService.getAllActivities();

        assertNotNull(activities);
        assertEquals(1, activities.size());
        assertEquals("Community Cleanup", activities.get(0).getDescription());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void testGetActivityById_WhenExists() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

        Optional<Activity> foundActivity = activityService.getActivityById(1L);

        assertTrue(foundActivity.isPresent());
        assertEquals("Community Cleanup", foundActivity.get().getDescription());
        verify(activityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetActivityById_WhenNotExists() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Activity> foundActivity = activityService.getActivityById(1L);

        assertFalse(foundActivity.isPresent());
        verify(activityRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveOrUpdateActivity() {
        when(activityRepository.save(activity)).thenReturn(activity);

        Activity savedActivity = activityService.saveOrUpdateActivity(activity);

        assertNotNull(savedActivity);
        assertEquals("Community Cleanup", savedActivity.getDescription());
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    void testDeleteActivity_WhenExists() {
        when(activityRepository.existsById(1L)).thenReturn(true);
        doNothing().when(activityRepository).deleteById(1L);

        assertDoesNotThrow(() -> activityService.deleteActivity(1L));
        verify(activityRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteActivity_WhenNotExists() {
        when(activityRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> activityService.deleteActivity(1L));
        assertEquals("Activity not found with id 1", exception.getMessage());
        verify(activityRepository, times(0)).deleteById(1L);
    }
}