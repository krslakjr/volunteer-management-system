package com.example.activitymanagement;

import com.example.activitymanagement.controller.ActivityController;
import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.service.ActivityService;
import com.example.activitymanagement.mapper.ActivityMapper;
import com.example.activitymanagement.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {

    @Mock
    private ActivityService activityService;

    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private ActivityController activityController;

    private MockMvc mockMvc;

    private Activity activity;
    private ActivityDTO activityDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(activityController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Dodajte GlobalExceptionHandler
                .build();

        activity = new Activity();
        activity.setActivityId(1L);
        activity.setDescription("Community Cleanup");
        activity.setLocation("City Park");
        activity.setDate("2025-04-10");
        activity.setVolunteersNeeded(20);

        activityDTO = new ActivityDTO();
        activityDTO.setActivityId(1L);
        activityDTO.setDescription("Community Cleanup");
        activityDTO.setLocation("City Park");
        activityDTO.setDate("2025-04-10");
        activityDTO.setVolunteersNeeded(20);
    }

    @Test
    void testGetAllActivities() throws Exception {
        when(activityService.getAllActivities()).thenReturn(Arrays.asList(activityDTO));

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activityId").value(1L))
                .andExpect(jsonPath("$[0].description").value("Community Cleanup"))
                .andExpect(jsonPath("$[0].location").value("City Park"))
                .andExpect(jsonPath("$[0].date").value("2025-04-10"))
                .andExpect(jsonPath("$[0].volunteersNeeded").value(20));

        verify(activityService, times(1)).getAllActivities();
    }

    @Test
    void testGetActivityById_Found() throws Exception {
        when(activityService.getActivityById(1L)).thenReturn(Optional.of(activityDTO));

        mockMvc.perform(get("/activities/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityId").value(1L))
                .andExpect(jsonPath("$.description").value("Community Cleanup"))
                .andExpect(jsonPath("$.location").value("City Park"))
                .andExpect(jsonPath("$.date").value("2025-04-10"))
                .andExpect(jsonPath("$.volunteersNeeded").value(20));

        verify(activityService, times(1)).getActivityById(1L);
    }

    @Test
void testGetActivityById_NotFound() throws Exception {
    when(activityService.getActivityById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/activities/{id}", 1L))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.errorType").value("not_found"))
            .andExpect(jsonPath("$.message").value("Activity not found"))
            .andExpect(jsonPath("$.field").doesNotExist()); 

    verify(activityService, times(1)).getActivityById(1L);
}

    @Test
    void testCreateActivity() throws Exception {
        when(activityService.createActivity(any(Activity.class))).thenReturn(activityDTO);

        mockMvc.perform(post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"description\": \"Community Cleanup\", \"date\": \"2025-04-10\", \"location\": \"City Park\", \"volunteersNeeded\": 20 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.activityId").value(1L))
                .andExpect(jsonPath("$.description").value("Community Cleanup"))
                .andExpect(jsonPath("$.location").value("City Park"))
                .andExpect(jsonPath("$.date").value("2025-04-10"))
                .andExpect(jsonPath("$.volunteersNeeded").value(20));

        verify(activityService, times(1)).createActivity(any(Activity.class));
    }

    @Test
    void testUpdateActivity() throws Exception {
        ActivityDTO updatedActivityDTO = new ActivityDTO();
        updatedActivityDTO.setDescription("Updated Cleanup");

        when(activityService.updateActivity(anyLong(), any(Activity.class))).thenReturn(updatedActivityDTO);

        mockMvc.perform(put("/activities/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Updated Cleanup\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Cleanup"));

        verify(activityService, times(1)).updateActivity(anyLong(), any(Activity.class));
    }

    @Test
    void testDeleteActivity() throws Exception {
        doNothing().when(activityService).deleteActivity(1L);

        mockMvc.perform(delete("/activities/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(activityService, times(1)).deleteActivity(1L);
    }
}
