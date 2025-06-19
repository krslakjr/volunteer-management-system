package com.example.notificationservice;

import com.example.notificationservice.models.Activity;
import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.repository.ActivityRepository;
import com.example.notificationservice.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity;

    @BeforeEach
    public void setUp() {
        Organizer organizer = new Organizer();
        organizer.setName("Organizer Name");

        activity = new Activity();
        activity.setTitle("Test Activity");
        activity.setDescription("Test Description");
        activity.setDate(new Date());
        activity.setLocation("Test Location");
        activity.setOrganizer(organizer);
    }

    @Test
    public void testSaveActivity() {
        activityService.saveActivity(activity);
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void testGetAllActivities() {
        Activity activity2 = new Activity();
        activity2.setTitle("Test Activity 2");
        activity2.setDescription("Test Description 2");
        activity2.setDate(new Date());
        activity2.setLocation("Test Location 2");
        
        Organizer organizer2 = new Organizer();
        organizer2.setName("Organizer 2");
        activity2.setOrganizer(organizer2);

        List<Activity> activities = Arrays.asList(activity, activity2);

        when(activityRepository.findAll()).thenReturn(activities);

        List<Activity> result = activityService.getAllActivities();
        assertEquals(2, result.size());
        assertTrue(result.contains(activity));
        assertTrue(result.contains(activity2));
    }

    @Test
    public void testGetActivityById_Found() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

        Optional<Activity> result = activityService.getActivityById(1L);
        assertTrue(result.isPresent());
        assertEquals(activity, result.get());
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
        assertEquals(activity, result);
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void testUpdateActivity_Found() {
        // Kreiranje novih podataka za ažuriranje
        Activity updatedActivity = new Activity();
        updatedActivity.setTitle("Updated Activity");
        updatedActivity.setDescription("Updated Description");
        updatedActivity.setDate(new Date());
        updatedActivity.setLocation("Updated Location");

        // Dodavanje organizatora sa set metodom
        Organizer updatedOrganizer = new Organizer();
        updatedOrganizer.setName("Updated Organizer");
        updatedActivity.setOrganizer(updatedOrganizer);

        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityRepository.save(any(Activity.class))).thenReturn(updatedActivity);

        // Pozivanje metode updateActivity
        activityService.updateActivity(1L, updatedActivity);

        // Koristimo ArgumentCaptor da bismo uhvatili objekat koji je prosleđen u save
        ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);
        verify(activityRepository, times(1)).save(captor.capture());

        // Uverite se da su podaci tačni
        Activity capturedActivity = captor.getValue();
        assertEquals("Updated Activity", capturedActivity.getTitle());
        assertEquals("Updated Description", capturedActivity.getDescription());
        assertEquals("Updated Location", capturedActivity.getLocation());
        assertEquals("Updated Organizer", capturedActivity.getOrganizer().getName());
    }

    @Test
    public void testUpdateActivity_NotFound() {
        Activity updatedActivity = new Activity();
        updatedActivity.setTitle("Updated Activity");
        updatedActivity.setDescription("Updated Description");
        updatedActivity.setDate(new Date());
        updatedActivity.setLocation("Updated Location");

        // Dodavanje organizatora sa set metodom
        Organizer updatedOrganizer = new Organizer();
        updatedOrganizer.setName("Updated Organizer");
        updatedActivity.setOrganizer(updatedOrganizer);

        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> activityService.updateActivity(1L, updatedActivity));
    }

    @Test
    public void testDeleteActivity() {
        activityService.deleteActivity(1L);
        verify(activityRepository, times(1)).deleteById(1L);
    }
}
