package com.example.activitymanagement;

import com.example.activitymanagement.controller.TeamController;
import com.example.activitymanagement.models.Team;
import com.example.activitymanagement.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setTeamId(1L);
        team.setTeamName("Team A");
    }

    @Test
    void testGetAllTeams() {
        when(teamService.getAllTeams()).thenReturn(List.of(team));

        List<Team> result = teamController.getAllTeams();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Team A", result.get(0).getTeamName());
        verify(teamService, times(1)).getAllTeams();
    }

    @Test
    void testGetTeamById_Found() {
        when(teamService.getTeamById(1L)).thenReturn(Optional.of(team));

        ResponseEntity<Team> result = teamController.getTeamById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Team A", result.getBody().getTeamName());
        verify(teamService, times(1)).getTeamById(1L);
    }

    @Test
    void testGetTeamById_NotFound() {
        when(teamService.getTeamById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Team> result = teamController.getTeamById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(teamService, times(1)).getTeamById(1L);
    }

    @Test
    void testCreateTeam() {
        when(teamService.createTeam(any(Team.class))).thenReturn(team);

        ResponseEntity<Team> result = teamController.createTeam(team);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Team A", result.getBody().getTeamName());
        verify(teamService, times(1)).createTeam(any(Team.class));
    }

    @Test
    void testCreateTeam_InternalServerError() {
        when(teamService.createTeam(any(Team.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Team> result = teamController.createTeam(team);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(teamService, times(1)).createTeam(any(Team.class));
    }

    @Test
    void testUpdateTeam_Found() {
        when(teamService.updateTeam(anyLong(), any(Team.class))).thenReturn(Optional.of(team));

        ResponseEntity<Team> result = teamController.updateTeam(1L, team);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Team A", result.getBody().getTeamName());
        verify(teamService, times(1)).updateTeam(anyLong(), any(Team.class));
    }

    @Test
    void testUpdateTeam_NotFound() {
        when(teamService.updateTeam(anyLong(), any(Team.class))).thenReturn(Optional.empty());

        ResponseEntity<Team> result = teamController.updateTeam(1L, team);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(teamService, times(1)).updateTeam(anyLong(), any(Team.class));
    }

    @Test
    void testDeleteTeam_Success() {
        when(teamService.deleteTeam(1L)).thenReturn(true);

        ResponseEntity<HttpStatus> result = teamController.deleteTeam(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(teamService, times(1)).deleteTeam(1L);
    }

    @Test
    void testDeleteTeam_NotFound() {
        when(teamService.deleteTeam(1L)).thenReturn(false);

        ResponseEntity<HttpStatus> result = teamController.deleteTeam(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(teamService, times(1)).deleteTeam(1L);
    }
}
