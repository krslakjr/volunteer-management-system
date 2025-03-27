package com.example.notificationservice;

import com.example.notificationservice.service.EngagementStatisticsService;
import com.example.notificationservice.models.EngagementStatistics;
import com.example.notificationservice.repository.EngagementStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EngagementStatisticsServiceTest {

    @Mock
    private EngagementStatisticsRepository engagementStatisticsRepository;

    @InjectMocks
    private EngagementStatisticsService engagementStatisticsService;

    private EngagementStatistics statistics;

    @BeforeEach
    void setUp() {
        statistics = new EngagementStatistics();
        statistics.setId(1L);
        statistics.setTotalActivities(5);
        statistics.setMessagesSent(10);
        statistics.setForumPostsMade(3);
        statistics.setNotificationsReceived(8);
    }

    @Test
    void testSaveEngagementStatistics() {
        engagementStatisticsService.saveEngagementStatistics(statistics);
        verify(engagementStatisticsRepository, times(1)).save(statistics);
    }

    @Test
    void testGetAllStatistics() {
        when(engagementStatisticsRepository.findAll()).thenReturn(List.of(statistics));

        List<EngagementStatistics> result = engagementStatisticsService.getAllStatistics();
        assertEquals(1, result.size());
        assertEquals(statistics.getId(), result.get(0).getId());

        verify(engagementStatisticsRepository, times(1)).findAll();
    }

    @Test
    void testGetStatisticsById_Found() {
        when(engagementStatisticsRepository.findById(1L)).thenReturn(Optional.of(statistics));

        Optional<EngagementStatistics> result = engagementStatisticsService.getStatisticsById(1L);
        assertTrue(result.isPresent());
        assertEquals(statistics.getId(), result.get().getId());

        verify(engagementStatisticsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStatisticsById_NotFound() {
        when(engagementStatisticsRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EngagementStatistics> result = engagementStatisticsService.getStatisticsById(1L);
        assertFalse(result.isPresent());

        verify(engagementStatisticsRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateStatistics() {
        when(engagementStatisticsRepository.save(any(EngagementStatistics.class))).thenReturn(statistics);

        EngagementStatistics result = engagementStatisticsService.createStatistics(statistics);
        assertNotNull(result);
        assertEquals(statistics.getId(), result.getId());

        verify(engagementStatisticsRepository, times(1)).save(statistics);
    }

    @Test
    void testUpdateStatistics_Found() {
        EngagementStatistics updatedStatistics = new EngagementStatistics();
        updatedStatistics.setTotalActivities(7);
        updatedStatistics.setMessagesSent(15);
        updatedStatistics.setForumPostsMade(5);
        updatedStatistics.setNotificationsReceived(12);

        when(engagementStatisticsRepository.findById(1L)).thenReturn(Optional.of(statistics));
        when(engagementStatisticsRepository.save(any(EngagementStatistics.class))).thenReturn(updatedStatistics);

        EngagementStatistics result = engagementStatisticsService.updateStatistics(1L, updatedStatistics);
        assertEquals(7, result.getTotalActivities());
        assertEquals(15, result.getMessagesSent());
        assertEquals(5, result.getForumPostsMade());
        assertEquals(12, result.getNotificationsReceived());

        verify(engagementStatisticsRepository, times(1)).findById(1L);
        verify(engagementStatisticsRepository, times(1)).save(any(EngagementStatistics.class));
    }

    @Test
    void testUpdateStatistics_NotFound() {
        when(engagementStatisticsRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            engagementStatisticsService.updateStatistics(1L, statistics);
        });

        assertEquals("EngagementStatistics not found", exception.getMessage());
        verify(engagementStatisticsRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteStatistics() {
        doNothing().when(engagementStatisticsRepository).deleteById(1L);

        engagementStatisticsService.deleteStatistics(1L);

        verify(engagementStatisticsRepository, times(1)).deleteById(1L);
    }
}