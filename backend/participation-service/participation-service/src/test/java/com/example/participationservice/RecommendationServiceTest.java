package com.example.participationservice;

import com.example.participationservice.service.RecommendationService;
import com.example.participationservice.models.Recommendation;
import com.example.participationservice.repository.RecommendationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    private Recommendation recommendation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendation = new Recommendation();
        recommendation.setRecommendationId(1L);
    }

    @Test
    void testGetAllRecommendations() {
        when(recommendationRepository.findAll()).thenReturn(List.of(recommendation));

        List<Recommendation> recommendations = recommendationService.getAllRecommendations();

        assertNotNull(recommendations);
        assertEquals(1, recommendations.size());
        verify(recommendationRepository, times(1)).findAll();
    }

    @Test
    void testSaveRecommendation() {
        when(recommendationRepository.save(any(Recommendation.class))).thenReturn(recommendation);

        recommendationService.saveRecommendation(recommendation);

        verify(recommendationRepository, times(1)).save(recommendation);
    }

    @Test
    void testGetRecommendationById() {
        when(recommendationRepository.findById(1L)).thenReturn(Optional.of(recommendation));

        Optional<Recommendation> result = recommendationService.getRecommendationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getRecommendationId());
        verify(recommendationRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateRecommendation() {
        when(recommendationRepository.save(any(Recommendation.class))).thenReturn(recommendation);

        Recommendation result = recommendationService.createRecommendation(recommendation);

        assertNotNull(result);
        assertEquals(1L, result.getRecommendationId());
        verify(recommendationRepository, times(1)).save(recommendation);
    }


    @Test
    void testDeleteRecommendation() {
        when(recommendationRepository.existsById(1L)).thenReturn(true);

        boolean result = recommendationService.deleteRecommendation(1L);

        assertTrue(result);
        verify(recommendationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRecommendationNotFound() {
        when(recommendationRepository.existsById(1L)).thenReturn(false);

        boolean result = recommendationService.deleteRecommendation(1L);

        assertFalse(result);
        verify(recommendationRepository, times(1)).existsById(1L);
    }
}
