package com.example.notificationservice;

import com.example.notificationservice.controller.ActivityController;
import com.example.notificationservice.models.Activity;
import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.service.ActivityService;
import com.example.notificationservice.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import java.time.LocalDateTime;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

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
        activity.setTitle("Activity 1");
    }


    @Test
    void testGetActivityById_Found() throws Exception {
        when(activityService.getActivityById(any(Long.class))).thenReturn(Optional.of(activity));

        mockMvc.perform(get("/activities/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityId").value(activity.getActivityId()))
                .andExpect(jsonPath("$.title").value(activity.getTitle()));

        verify(activityService, times(1)).getActivityById(1L);
    }

    @Test
void testGetActivityById_NotFound() throws Exception {
    when(activityService.getActivityById(any(Long.class))).thenReturn(Optional.empty());

    mockMvc.perform(get("/activities/{id}", 1L))
        .andExpect(status().isNotFound()) 
        .andExpect(jsonPath("$.message").value("Activity not found with id 1")) 
        .andExpect(jsonPath("$.errorType").value("Resource Not Found")) 
        .andDo(MockMvcResultHandlers.print());

    verify(activityService, times(1)).getActivityById(1L);
}



    @Test
    void testDeleteActivity() throws Exception {
        doNothing().when(activityService).deleteActivity(any(Long.class));

        mockMvc.perform(delete("/activities/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(activityService, times(1)).deleteActivity(any(Long.class));
    }
}

