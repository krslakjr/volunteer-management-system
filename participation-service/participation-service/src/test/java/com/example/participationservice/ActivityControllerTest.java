package com.example.participationservice;

import com.example.participationservice.controller.ActivityController;
import com.example.participationservice.models.Activity;
import com.example.participationservice.service.ActivityService;

import com.example.participationservice.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

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
        when(activityService.getAllActivities()).thenReturn(Arrays.asList(activity));

        var response = activityController.getAllActivities();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Activity Description", response.get(0).getDescription());
    }

    @Test
    public void testGetActivityById_Found() {
        when(activityService.getActivityById(1L)).thenReturn(Optional.of(activity));

        ResponseEntity<Activity> response = activityController.getActivityById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Activity Description", response.getBody().getDescription());
    }

    @Test
public void testGetActivityById_NotFound() {
    when(activityService.getActivityById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
        activityController.getActivityById(1L);
    });

    assertTrue(exception.getMessage().contains("Activity not found with id 1"));
}


    @Test
    public void testCreateActivity() {
        when(activityService.createActivity(any(Activity.class))).thenReturn(activity);

        ResponseEntity<Activity> response = activityController.createActivity(activity);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Activity Description", response.getBody().getDescription());
        verify(activityService, times(1)).createActivity(any(Activity.class));
    }

    @Test
    public void testUpdateActivity_Found() {
        when(activityService.updateActivity(eq(1L), any(Activity.class))).thenReturn(activity);

        ResponseEntity<Activity> response = activityController.updateActivity(1L, activity);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Activity Description", response.getBody().getDescription());
    }

    @Test
    public void testUpdateActivity_NotFound() {
        when(activityService.updateActivity(eq(1L), any(Activity.class))).thenReturn(null);

        ResponseEntity<Activity> response = activityController.updateActivity(1L, activity);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void testDeleteActivity_Success() {
        when(activityService.deleteActivity(1L)).thenReturn(true);

        ResponseEntity<Void> response = activityController.deleteActivity(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(activityService, times(1)).deleteActivity(1L);
    }

    @Test
    public void testDeleteActivity_NotFound() {
        when(activityService.deleteActivity(1L)).thenReturn(false);

        ResponseEntity<Void> response = activityController.deleteActivity(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
    }
}
