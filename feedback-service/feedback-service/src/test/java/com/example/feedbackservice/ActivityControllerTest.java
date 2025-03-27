package com.example.feedbackservice;

import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.controller.ActivityController;
import com.example.feedbackservice.service.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ActivityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();
    }

    @Test
    void testGetAllActivities() throws Exception {
        // Arrange
        Activity a1 = new Activity();
        a1.setActivityId(1L);
        a1.setDescription("Planting trees");

        Activity a2 = new Activity();
        a2.setActivityId(2L);
        a2.setDescription("Cleaning park");

        when(activityService.getAllActivities()).thenReturn(Arrays.asList(a1, a2));

        // Act & Assert
        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].description").value("Planting trees"))
                .andExpect(jsonPath("$[1].description").value("Cleaning park"));

        verify(activityService, times(1)).getAllActivities();
    }

    @Test
    void testGetActivityById_Found() throws Exception {
        // Arrange
        Activity activity = new Activity();
        activity.setActivityId(1L);
        activity.setDescription("Beach Cleanup");

        when(activityService.getActivityById(1L)).thenReturn(Optional.of(activity));

        // Act & Assert
        mockMvc.perform(get("/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Beach Cleanup"));

        verify(activityService, times(1)).getActivityById(1L);
    }

    @Test
    void testGetActivityById_NotFound() throws Exception {
        // Arrange
        when(activityService.getActivityById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/activities/1"))
                .andExpect(status().isNotFound());

        verify(activityService, times(1)).getActivityById(1L);
    }

    @Test
    void testCreateOrUpdateActivity() throws Exception {
        // Arrange
        Activity activity = new Activity();
        activity.setActivityId(1L);
        activity.setDescription("Fundraising event");
        activity.setDate(new Date());
        activity.setLocation("Community Hall");
        activity.setVolunteersNeeded(10);

        when(activityService.saveOrUpdateActivity(any(Activity.class))).thenReturn(activity);

        // Act & Assert
        mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Fundraising event"))
                .andExpect(jsonPath("$.location").value("Community Hall"));

        verify(activityService, times(1)).saveOrUpdateActivity(any(Activity.class));
    }

    @Test
    void testDeleteActivity_Success() throws Exception {
        // Arrange
        doNothing().when(activityService).deleteActivity(1L);

        // Act & Assert
        mockMvc.perform(delete("/activities/1"))
                .andExpect(status().isNoContent());

        verify(activityService, times(1)).deleteActivity(1L);
    }

    @Test
    void testDeleteActivity_Failure() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Database error")).when(activityService).deleteActivity(1L);

        // Act & Assert
        mockMvc.perform(delete("/activities/1"))
                .andExpect(status().isInternalServerError());

        verify(activityService, times(1)).deleteActivity(1L);
    }
}
