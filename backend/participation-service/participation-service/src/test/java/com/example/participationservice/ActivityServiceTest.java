package com.example.participationservice;

import com.example.participationservice.service.ActivityService;
import com.example.participationservice.models.Activity;
import com.example.participationservice.repository.ActivityRepository;
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
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity;

    @BeforeEach
    public void setUp() {
        activity = new Activity();
        activity.setActivityId(1L);
        activity.setDescription("Activity Description");
        activity.setLocation("Activity Location");
        activity.setVolunteersNeeded(10);
    }

    @Test
    public void testGetAllActivities() {
        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity));

        var activities = activityService.getAllActivities();

        assertNotNull(activities);
        assertEquals(1, activities.size());
        assertEquals("Activity Description", activities.get(0).getDescription());
    }

    @Test
    public void testGetActivityById_Found() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

        Optional<Activity> foundActivity = activityService.getActivityById(1L);

        assertTrue(foundActivity.isPresent());
        assertEquals("Activity Description", foundActivity.get().getDescription());
    }

    @Test
    public void testGetActivityById_NotFound() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Activity> foundActivity = activityService.getActivityById(1L);

        assertFalse(foundActivity.isPresent());
    }

    @Test
    public void testCreateActivity() {
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        Activity createdActivity = activityService.createActivity(activity);

        assertNotNull(createdActivity);
        assertEquals("Activity Description", createdActivity.getDescription());
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    public void testUpdateActivity_Found() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        Activity updatedActivity = activityService.updateActivity(1L, activity);

        assertNotNull(updatedActivity);
        assertEquals("Activity Description", updatedActivity.getDescription());
    }

    @Test
    public void testUpdateActivity_NotFound() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        Activity updatedActivity = activityService.updateActivity(1L, activity);

        assertNull(updatedActivity);
    }

    @Test
    public void testDeleteActivity_Success() {
        when(activityRepository.existsById(1L)).thenReturn(true);

        boolean result = activityService.deleteActivity(1L);

        assertTrue(result);
        verify(activityRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteActivity_NotFound() {
        when(activityRepository.existsById(1L)).thenReturn(false);

        boolean result = activityService.deleteActivity(1L);

        assertFalse(result);
    }
}
