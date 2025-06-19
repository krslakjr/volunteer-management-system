package com.example.activitymanagement;

import com.example.activitymanagement.models.*;
import com.example.activitymanagement.service.TeamActivityService;
import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.repository.TeamActivityRepository;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

        TeamActivity result = teamActivityService.getTeamActivityById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(teamActivityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTeamActivityById_NotFound() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamActivityService.getTeamActivityById(1L));
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

        TeamActivity result = teamActivityService.updateTeamActivity(1L, teamActivity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(teamActivityRepository, times(1)).findById(1L);
        verify(teamActivityRepository, times(1)).save(any(TeamActivity.class));
    }

    @Test
    void testUpdateTeamActivity_NotFound() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamActivityService.updateTeamActivity(1L, teamActivity));
        verify(teamActivityRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTeamActivity_Success() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.of(teamActivity));

        assertDoesNotThrow(() -> teamActivityService.deleteTeamActivity(1L));
        verify(teamActivityRepository, times(1)).findById(1L);
        verify(teamActivityRepository, times(1)).delete(any(TeamActivity.class));
    }

    @Test
    void testDeleteTeamActivity_NotFound() {
        when(teamActivityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teamActivityService.deleteTeamActivity(1L));
        verify(teamActivityRepository, times(1)).findById(1L);
    }
}
