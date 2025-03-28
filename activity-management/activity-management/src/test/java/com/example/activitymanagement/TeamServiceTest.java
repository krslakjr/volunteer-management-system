package com.example.activitymanagement;

import com.example.activitymanagement.service.TeamService;
import com.example.activitymanagement.models.Team;
import com.example.activitymanagement.repository.TeamRepository;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setTeamId(1L);
        team.setTeamName("Team A");
    }

    @Test
    void testGetAllTeams() {
        when(teamRepository.findAll()).thenReturn(List.of(team));

        List<Team> result = teamService.getAllTeams();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getTeamId());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void testGetTeamById_Found() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        Team result = teamService.getTeamById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTeamId());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTeamById_NotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamService.getTeamById(1L));
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTeam() {
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Team result = teamService.createTeam(team);

        assertNotNull(result);
        assertEquals(1L, result.getTeamId());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testUpdateTeam_Found() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Optional<Team> result = teamService.updateTeam(1L, team);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getTeamId());
        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testUpdateTeam_NotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Team> result = teamService.updateTeam(1L, team);

        assertFalse(result.isPresent());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTeam_Success() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        boolean result = teamService.deleteTeam(1L);

        assertTrue(result);
        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(1)).delete(any(Team.class));
    }

    @Test
    void testDeleteTeam_NotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamService.deleteTeam(1L));
        verify(teamRepository, times(1)).findById(1L);
    }
}
