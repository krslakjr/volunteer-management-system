package com.example.activitymanagement;

import com.example.activitymanagement.controller.ActivityVolunteerController;
import com.example.activitymanagement.dto.ActivityVolunteerDTO;
import com.example.activitymanagement.exception.GlobalExceptionHandler;
import com.example.activitymanagement.service.ActivityVolunteerService;
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
class ActivityVolunteerControllerTest {

    @Mock
    private ActivityVolunteerService activityVolunteerService;

    @InjectMocks
    private ActivityVolunteerController activityVolunteerController;

    private MockMvc mockMvc;

    private ActivityVolunteerDTO activityVolunteerDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(activityVolunteerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        activityVolunteerDTO = new ActivityVolunteerDTO();
        activityVolunteerDTO.setId(1L);
        activityVolunteerDTO.setActivityId(1L);
        activityVolunteerDTO.setVolunteerId(1L);
    }

    @Test
    void testGetAllActivityVolunteers() throws Exception {
        when(activityVolunteerService.getAllActivityVolunteers()).thenReturn(Arrays.asList(activityVolunteerDTO));

        mockMvc.perform(get("/activity-volunteers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].activityId").value(1L))
                .andExpect(jsonPath("$[0].volunteerId").value(1L));

        verify(activityVolunteerService, times(1)).getAllActivityVolunteers();
    }

    @Test
    void testGetActivityVolunteerById_Found() throws Exception {
        when(activityVolunteerService.getActivityVolunteerById(1L)).thenReturn(Optional.of(activityVolunteerDTO));

        mockMvc.perform(get("/activity-volunteers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.activityId").value(1L))
                .andExpect(jsonPath("$.volunteerId").value(1L));

        verify(activityVolunteerService, times(1)).getActivityVolunteerById(1L);
    }

    @Test
    void testGetActivityVolunteerById_NotFound() throws Exception {
        when(activityVolunteerService.getActivityVolunteerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/activity-volunteers/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorType").value("not_found"))
                .andExpect(jsonPath("$.message").value("Volonter s ID-jem 1 nije pronađen"))
                .andExpect(jsonPath("$.field").doesNotExist()) 
                .andExpect(jsonPath("$.timestamp").exists());

        verify(activityVolunteerService, times(1)).getActivityVolunteerById(1L);
    }

    @Test
    void testCreateActivityVolunteer() throws Exception {
        when(activityVolunteerService.createActivityVolunteer(any(ActivityVolunteerDTO.class))).thenReturn(activityVolunteerDTO);

        mockMvc.perform(post("/activity-volunteers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"activityId\": 1, \"volunteerId\": 1 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.activityId").value(1L))
                .andExpect(jsonPath("$.volunteerId").value(1L));

        verify(activityVolunteerService, times(1)).createActivityVolunteer(any(ActivityVolunteerDTO.class));
    }

    @Test
void testCreateActivityVolunteer_InvalidData() throws Exception {
    mockMvc.perform(post("/activity-volunteers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"activityId\": null, \"volunteerId\": 1 }")) 
            .andExpect(status().isBadRequest()) 
            .andExpect(jsonPath("$.errorType").value("validation_error")) 
            .andExpect(jsonPath("$.message").value("Activity ID is required"))  
            .andExpect(jsonPath("$.field").value("activityId")); 
}


    @Test
    void testUpdateActivityVolunteer() throws Exception {
        when(activityVolunteerService.updateActivityVolunteer(anyLong(), any(ActivityVolunteerDTO.class)))
                .thenReturn(Optional.of(activityVolunteerDTO));

        mockMvc.perform(put("/activity-volunteers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"activityId\": 1, \"volunteerId\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.activityId").value(1L))
                .andExpect(jsonPath("$.volunteerId").value(1L));

        verify(activityVolunteerService, times(1)).updateActivityVolunteer(anyLong(), any(ActivityVolunteerDTO.class));
    }

    @Test
    void testUpdateActivityVolunteer_NotFound() throws Exception {
        when(activityVolunteerService.updateActivityVolunteer(anyLong(), any(ActivityVolunteerDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/activity-volunteers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"activityId\": 1, \"volunteerId\": 1 }"))
                .andExpect(status().isNotFound());

        verify(activityVolunteerService, times(1)).updateActivityVolunteer(anyLong(), any(ActivityVolunteerDTO.class));
    }

    @Test
    void testDeleteActivityVolunteer() throws Exception {
        when(activityVolunteerService.deleteActivityVolunteer(1L)).thenReturn(true);

        mockMvc.perform(delete("/activity-volunteers/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(activityVolunteerService, times(1)).deleteActivityVolunteer(1L);
    }

    @Test
    void testDeleteActivityVolunteer_NotFound() throws Exception {
        when(activityVolunteerService.deleteActivityVolunteer(1L)).thenReturn(false);

        mockMvc.perform(delete("/activity-volunteers/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(activityVolunteerService, times(1)).deleteActivityVolunteer(1L);
    }
}
