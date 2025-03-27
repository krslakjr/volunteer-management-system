package com.example.participationservice;

import com.example.participationservice.controller.RecommendationController;
import com.example.participationservice.models.Recommendation;
import com.example.participationservice.service.RecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private RecommendationController recommendationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController).build();
    }

    @Test
    void testGetAllRecommendations() throws Exception {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(1L);
        recommendation.setDateGenerated(new Date());

        when(recommendationService.getAllRecommendations()).thenReturn(Arrays.asList(recommendation));

        mockMvc.perform(get("/recommendations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recommendationId").value(1L));
    }

    @Test
    void testGetRecommendationById_Found() throws Exception {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(1L);
        recommendation.setDateGenerated(new Date());

        when(recommendationService.getRecommendationById(1L)).thenReturn(Optional.of(recommendation));

        mockMvc.perform(get("/recommendations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendationId").value(1L));
    }

    @Test
    void testGetRecommendationById_NotFound() throws Exception {
        when(recommendationService.getRecommendationById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/recommendations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateRecommendation() throws Exception {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(1L);
        recommendation.setDateGenerated(new Date());

        when(recommendationService.createRecommendation(any(Recommendation.class))).thenReturn(recommendation);

        mockMvc.perform(post("/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendationId").value(1L));
    }

    @Test
    void testUpdateRecommendation_Found() throws Exception {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(1L);
        recommendation.setDateGenerated(new Date());

        when(recommendationService.updateRecommendation(eq(1L), any(Recommendation.class))).thenReturn(recommendation);

        mockMvc.perform(put("/recommendations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendationId").value(1L));
    }

    @Test
    void testDeleteRecommendation_Found() throws Exception {
        when(recommendationService.deleteRecommendation(1L)).thenReturn(true);

        mockMvc.perform(delete("/recommendations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRecommendation_NotFound() throws Exception {
        when(recommendationService.deleteRecommendation(1L)).thenReturn(false);

        mockMvc.perform(delete("/recommendations/1"))
                .andExpect(status().isNotFound());
    }
}
