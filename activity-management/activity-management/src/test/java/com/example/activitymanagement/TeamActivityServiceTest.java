package com.example.activitymanagement;

import com.example.activitymanagement.models.*;
import com.example.activitymanagement.service.TeamActivityService;
import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.repository.TeamActivityRepository;
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
class TeamActivityServiceTest {

    @Mock
    private TeamActivityRepository teamActivityRepository;

    @InjectMocks
    private TeamActivityService teamActivityService;

    private TeamActivity teamActivity;

    @BeforeEach
    void setUp() {
        teamActivity = new TeamActivity();
        teamActivity.setId(1L);
        // Assuming you have setters for team and activity
        teamActivity.setTeam(new Team());
        teamActivity.setActivity(new Activity());
    }

    @Test
    void testGetAllTeamActivities() {
        when(teamActivityRepository.findAll()).thenReturn(List.of(teamActivity));

        List<TeamActivity> result = teamActivityService.getAllTeamActivities();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(teamActivityRepository, times(1)).findAll();
    }

    @Test
    void testGetTeamActivityById_Found() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.of(teamActivity));

        Optional<TeamActivity> result = teamActivityService.getTeamActivityById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(teamActivityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTeamActivityById_NotFound() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TeamActivity> result = teamActivityService.getTeamActivityById(1L);

        assertFalse(result.isPresent());
        verify(teamActivityRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTeamActivity() {
        when(teamActivityRepository.save(any(TeamActivity.class))).thenReturn(teamActivity);

        TeamActivity result = teamActivityService.createTeamActivity(teamActivity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(teamActivityRepository, times(1)).save(any(TeamActivity.class));
    }

    @Test
    void testUpdateTeamActivity_Found() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.of(teamActivity));
        when(teamActivityRepository.save(any(TeamActivity.class))).thenReturn(teamActivity);

        Optional<TeamActivity> result = teamActivityService.updateTeamActivity(1L, teamActivity);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(teamActivityRepository, times(1)).findById(1L);
        verify(teamActivityRepository, times(1)).save(any(TeamActivity.class));
    }

    @Test
    void testUpdateTeamActivity_NotFound() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TeamActivity> result = teamActivityService.updateTeamActivity(1L, teamActivity);

        assertFalse(result.isPresent());
        verify(teamActivityRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTeamActivity_Success() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.of(teamActivity));

        boolean result = teamActivityService.deleteTeamActivity(1L);

        assertTrue(result);
        verify(teamActivityRepository, times(1)).findById(1L);
        verify(teamActivityRepository, times(1)).delete(any(TeamActivity.class));
    }

    @Test
    void testDeleteTeamActivity_NotFound() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = teamActivityService.deleteTeamActivity(1L);

        assertFalse(result);
        verify(teamActivityRepository, times(1)).findById(1L);
    }
}
