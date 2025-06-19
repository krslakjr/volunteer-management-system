package com.example.userservice;

import com.example.userservice.service.ActivityService;
import com.example.userservice.models.Activity;
import com.example.userservice.models.User;
import com.example.userservice.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.userservice.exception.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity;
    private User organizer;

    @BeforeEach
    public void setUp() {
        organizer = new User();

        activity = new Activity();
        activity.setActivityName("Test Activity");
        activity.setActivityDate(new Date());
        activity.setDescription("Test Description");
        activity.setOrganizer(organizer);
    }

    @Test
    public void testGetAllActivities() {
        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity));

        List<Activity> result = activityService.getAllActivities();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Activity", result.get(0).getActivityName());
    }

    @Test
    public void testGetActivityById_Found() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

        Optional<Activity> result = activityService.getActivityById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Activity", result.get().getActivityName());
    }

    @Test
    public void testGetActivityById_NotFound() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Activity> result = activityService.getActivityById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateActivity() {
        when(activityRepository.save(activity)).thenReturn(activity);

        Activity result = activityService.createActivity(activity);
        assertNotNull(result);
        assertEquals("Test Activity", result.getActivityName());
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
public void testUpdateActivity_Found() {
    when(activityRepository.findById(1L)).thenReturn(Optional.of(activity)); 
    when(activityRepository.save(activity)).thenReturn(activity); 

    Activity result = activityService.updateActivity(1L, activity);
    assertNotNull(result); 
    assertEquals("Test Activity", result.getActivityName()); 
    verify(activityRepository, times(1)).save(activity); 
}

    @Test
public void testUpdateActivity_NotFound() {
    when(activityRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> {
        activityService.updateActivity(1L, activity);
    });
}


    @Test
public void testDeleteActivity() {
    when(activityRepository.existsById(1L)).thenReturn(true);

    doNothing().when(activityRepository).deleteById(1L); 

    activityService.deleteActivity(1L); 

    verify(activityRepository, times(1)).deleteById(1L);
}

}
