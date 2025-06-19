package com.example.feedbackservice;

import com.example.feedbackservice.controller.ActivityStatisticsController;
import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.ActivityStatistics;
import com.example.feedbackservice.service.ActivityStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class ActivityStatisticsControllerTest {

    @Mock
    private ActivityStatisticsService activityStatisticsService;

    @InjectMocks
    private ActivityStatisticsController activityStatisticsController;

    private ActivityStatistics statistics;

    @BeforeEach
    void setUp() {
        statistics = new ActivityStatistics();
        statistics.setId(1L);
        statistics.setAverageRating(4.5);
        statistics.setTotalRatings(10);
        statistics.setTotalComments(5);
    }

    @Test
    void testGetAllActivityStatistics() {
        List<ActivityStatistics> statisticsList = Arrays.asList(statistics);
        when(activityStatisticsService.getAllActivityStatistics()).thenReturn(statisticsList);

        ResponseEntity<List<ActivityStatistics>> response = activityStatisticsController.getAllActivityStatistics();

        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(activityStatisticsService, times(1)).getAllActivityStatistics();
    }

    @Test
    void testGetActivityStatisticsById_Found() {
        when(activityStatisticsService.getActivityStatisticsById(1L)).thenReturn(Optional.of(statistics));

        ResponseEntity<ActivityStatistics> response = activityStatisticsController.getActivityStatisticsById(1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals(statistics, response.getBody());
        verify(activityStatisticsService, times(1)).getActivityStatisticsById(1L);
    }

    @Test
void testGetActivityStatisticsById_NotFound() {
    when(activityStatisticsService.getActivityStatisticsById(1L)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> 
        activityStatisticsController.getActivityStatisticsById(1L));

    assertEquals("Activity Statistics not found with id 1", exception.getMessage());
    verify(activityStatisticsService, times(1)).getActivityStatisticsById(1L);
}


    @Test
    void testCreateOrUpdateActivityStatistics_Success() {
        when(activityStatisticsService.saveOrUpdateActivityStatistics(statistics)).thenReturn(statistics);

        ResponseEntity<ActivityStatistics> response = activityStatisticsController.createOrUpdateActivityStatistics(statistics);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals(statistics, response.getBody());
        verify(activityStatisticsService, times(1)).saveOrUpdateActivityStatistics(statistics);
    }

    @Test
    void testCreateOrUpdateActivityStatistics_Exception() {
        when(activityStatisticsService.saveOrUpdateActivityStatistics(statistics)).thenThrow(new RuntimeException());

        ResponseEntity<ActivityStatistics> response = activityStatisticsController.createOrUpdateActivityStatistics(statistics);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(activityStatisticsService, times(1)).saveOrUpdateActivityStatistics(statistics);
    }

    @Test
    void testDeleteActivityStatistics_Success() {
        doNothing().when(activityStatisticsService).deleteActivityStatistics(1L);

        ResponseEntity<HttpStatus> response = activityStatisticsController.deleteActivityStatistics(1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(activityStatisticsService, times(1)).deleteActivityStatistics(1L);
    }

    @Test
    void testDeleteActivityStatistics_Exception() {
        doThrow(new RuntimeException()).when(activityStatisticsService).deleteActivityStatistics(1L);

        ResponseEntity<HttpStatus> response = activityStatisticsController.deleteActivityStatistics(1L);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(activityStatisticsService, times(1)).deleteActivityStatistics(1L);
    }
}
