package com.example.activitymanagement;

import com.example.activitymanagement.models.*;
import com.example.activitymanagement.controller.TeamActivityController;
import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.service.TeamActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamActivityControllerTest {

    @Mock
    private TeamActivityService teamActivityService;

    @InjectMocks
    private TeamActivityController teamActivityController;

    private TeamActivity teamActivity;

    @BeforeEach
    void setUp() {
        teamActivity = new TeamActivity();
        teamActivity.setId(1L);
        teamActivity.setTeam(new Team());
        teamActivity.setActivity(new Activity());
    }

    @Test
    void testGetAllTeamActivities() {
        when(teamActivityService.getAllTeamActivities()).thenReturn(List.of(teamActivity));

        ResponseEntity<List<TeamActivity>> result = teamActivityController.getAllTeamActivities();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(teamActivityService, times(1)).getAllTeamActivities();
    }

    @Test
    void testGetTeamActivityById_Found() {
        when(teamActivityService.getTeamActivityById(1L)).thenReturn(teamActivity);  // Ovdje koristimo direktan objekat, ne Optional

        ResponseEntity<TeamActivity> result = teamActivityController.getTeamActivityById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        verify(teamActivityService, times(1)).getTeamActivityById(1L);
    }

    @Test
    void testCreateTeamActivity() {
        when(teamActivityService.createTeamActivity(any(TeamActivity.class))).thenReturn(teamActivity);

        ResponseEntity<TeamActivity> result = teamActivityController.createTeamActivity(teamActivity);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(1L, result.getBody().getId());
        verify(teamActivityService, times(1)).createTeamActivity(any(TeamActivity.class));
    }

    @Test
    void testUpdateTeamActivity_Found() {
        when(teamActivityService.updateTeamActivity(anyLong(), any(TeamActivity.class))).thenReturn(teamActivity); // Direktan objekat, ne Optional

        ResponseEntity<TeamActivity> result = teamActivityController.updateTeamActivity(1L, teamActivity);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1L, result.getBody().getId());
        verify(teamActivityService, times(1)).updateTeamActivity(anyLong(), any(TeamActivity.class));
    }

    @Test
    void testDeleteTeamActivity_Success() {
        doNothing().when(teamActivityService).deleteTeamActivity(1L);  // Metoda ne vraća boolean

        ResponseEntity<Void> result = teamActivityController.deleteTeamActivity(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(teamActivityService, times(1)).deleteTeamActivity(1L);
    }
}

