package com.example.feedbackservice;

import com.example.feedbackservice.service.ActivityStatisticsService;
import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.ActivityStatistics;
import com.example.feedbackservice.repository.ActivityStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityStatisticsServiceTest {

    @Mock
    private ActivityStatisticsRepository activityStatisticsRepository;

    @InjectMocks
    private ActivityStatisticsService activityStatisticsService;

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
        when(activityStatisticsRepository.findAll()).thenReturn(statisticsList);

        List<ActivityStatistics> result = activityStatisticsService.getAllActivityStatistics();

        assertEquals(1, result.size());
        assertEquals(statistics.getId(), result.get(0).getId());
        verify(activityStatisticsRepository, times(1)).findAll();
    }

    @Test
    void testGetActivityStatisticsById_Found() {
        when(activityStatisticsRepository.findById(1L)).thenReturn(Optional.of(statistics));

        Optional<ActivityStatistics> result = activityStatisticsService.getActivityStatisticsById(1L);

        assertTrue(result.isPresent());
        assertEquals(statistics.getId(), result.get().getId());
        verify(activityStatisticsRepository, times(1)).findById(1L);
    }

    @Test
void testGetActivityStatisticsById_NotFound() {
    when(activityStatisticsRepository.findById(1L)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> 
        activityStatisticsService.getActivityStatisticsById(1L));

    assertEquals("Activity Statistics not found with id 1", exception.getMessage());
    verify(activityStatisticsRepository, times(1)).findById(1L);
}


    @Test
    void testSaveOrUpdateActivityStatistics() {
        when(activityStatisticsRepository.save(statistics)).thenReturn(statistics);

        ActivityStatistics result = activityStatisticsService.saveOrUpdateActivityStatistics(statistics);

        assertNotNull(result);
        assertEquals(statistics.getId(), result.getId());
        verify(activityStatisticsRepository, times(1)).save(statistics);
    }

    @Test
    void testDeleteActivityStatistics_Success() {
        when(activityStatisticsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(activityStatisticsRepository).deleteById(1L);

        assertDoesNotThrow(() -> activityStatisticsService.deleteActivityStatistics(1L));

        verify(activityStatisticsRepository, times(1)).existsById(1L);
        verify(activityStatisticsRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteActivityStatistics_NotFound() {
        when(activityStatisticsRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> activityStatisticsService.deleteActivityStatistics(1L));

        assertEquals("Activity Statistics not found with id 1", exception.getMessage());
        verify(activityStatisticsRepository, times(1)).existsById(1L);
        verify(activityStatisticsRepository, never()).deleteById(1L);
    }
}
