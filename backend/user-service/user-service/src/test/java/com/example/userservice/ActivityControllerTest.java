package com.example.userservice;

import com.example.userservice.exception.GlobalExceptionHandler;
import com.example.userservice.controller.ActivityController;
import com.example.userservice.models.Activity;
import com.example.userservice.service.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.userservice.exception.*;
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
import java.util.List;
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

    private Activity activity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(activityController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();

        activity = new Activity();
        activity.setActivityId(1L);
        activity.setActivityName("Tree Planting");
        activity.setActivityDate(new Date());
        activity.setDescription("Planting trees in the city park.");
    }

    @Test
    void testGetAllActivities() throws Exception {
        List<Activity> activities = Arrays.asList(activity);
        when(activityService.getAllActivities()).thenReturn(activities);

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].activityName").value("Tree Planting"));

        verify(activityService, times(1)).getAllActivities();
    }

    @Test
    void testGetActivityById_Found() throws Exception {
        when(activityService.getActivityById(1L)).thenReturn(Optional.of(activity));

        mockMvc.perform(get("/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityName").value("Tree Planting"));

        verify(activityService, times(1)).getActivityById(1L);
    }

    @Test
void testGetActivityById_NotFound() throws Exception {
    when(activityService.getActivityById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/activities/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.errorType").value("Resource Not Found"))
            .andExpect(jsonPath("$.message").value("Activity not found with id 1"))
            .andExpect(jsonPath("$.field").value("id"));

    verify(activityService, times(1)).getActivityById(1L);
}

    @Test
    void testCreateActivity() throws Exception {
        when(activityService.createActivity(any(Activity.class))).thenReturn(activity);

        mockMvc.perform(post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(activity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.activityName").value("Tree Planting"));

        verify(activityService, times(1)).createActivity(any(Activity.class));
    }

    @Test
void testUpdateActivity_Found() throws Exception {
    when(activityService.updateActivity(eq(1L), any(Activity.class))).thenReturn(activity);

    mockMvc.perform(put("/activities/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(activity)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.activityName").value("Tree Planting"));

    verify(activityService, times(1)).updateActivity(eq(1L), any(Activity.class));
}

@Test
void testUpdateActivity_NotFound() throws Exception {
    when(activityService.updateActivity(eq(1L), any(Activity.class)))
            .thenThrow(new ResourceNotFoundException("Activity not found with id 1", "id"));

    mockMvc.perform(put("/activities/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(activity)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.errorType").value("Resource Not Found"))
            .andExpect(jsonPath("$.message").value("Activity not found with id 1"))
            .andExpect(jsonPath("$.field").value("id"));

    verify(activityService, times(1)).updateActivity(eq(1L), any(Activity.class));
}


    @Test
    void testDeleteActivity() throws Exception {
        doNothing().when(activityService).deleteActivity(1L);

        mockMvc.perform(delete("/activities/1"))
                .andExpect(status().isNoContent());

        verify(activityService, times(1)).deleteActivity(1L);
    }
}
