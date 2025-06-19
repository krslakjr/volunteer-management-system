package com.example.participationservice.service;

import com.example.participationservice.exception.InvalidPatchException;
import com.example.participationservice.exception.ResourceNotFoundException;
import com.example.participationservice.logging.LoggableAction;
import com.example.participationservice.models.Recommendation;
import com.example.participationservice.repository.RecommendationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import java.util.List;


@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @LoggableAction
    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    public void saveRecommendation(Recommendation recommendation) {
        recommendationRepository.save(recommendation);
    }

    public Optional<Recommendation> getRecommendationById(Long id) {
        return recommendationRepository.findById(id);
    }

    @LoggableAction
    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public Recommendation updateRecommendation(Long id, Recommendation recommendationDetails) {
        Optional<Recommendation> optionalRecommendation = recommendationRepository.findById(id);

        if (optionalRecommendation.isPresent()) {
            Recommendation recommendation = optionalRecommendation.get();
            recommendation.setVolunteer(recommendationDetails.getVolunteer());
            recommendation.setRecommendationActivity(recommendationDetails.getRecommendationActivity());
            recommendation.setDateGenerated(recommendationDetails.getDateGenerated());
            return recommendationRepository.save(recommendation);
        }
        return null;
    }

    public boolean deleteRecommendation(Long id) {
        if (recommendationRepository.existsById(id)) {
            recommendationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @LoggableAction
    @Transactional
    public Recommendation applyPatchToRecommendation(Long id, JsonPatch patch) {
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id " + id, "id"));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(recommendation, JsonNode.class));
            Recommendation updatedRecommendation = objectMapper.treeToValue(patched, Recommendation.class);

            // Validacija
            if (updatedRecommendation.getDateGenerated() == null) {
                throw new InvalidPatchException("Date generated cannot be null.");
            }
            if (updatedRecommendation.getDateGenerated().after(new Date())) {
                throw new InvalidPatchException("Date generated cannot be in the future.");
            }

            updatedRecommendation.setRecommendationId(id);
            return recommendationRepository.save(updatedRecommendation);
        } catch (JsonPatchException e) {
            throw new InvalidPatchException("Invalid JSON Patch format: " + e.getMessage());
        } catch (Exception e) {
            throw new InvalidPatchException("Error processing patch request: " + e.getMessage());
        }
    }

    @LoggableAction
    public List<Recommendation> getRecommendationsByVolunteer(Long volunteerId) {
        List<Recommendation> recommendations = recommendationRepository.findByVolunteer_VolunteerId(volunteerId);
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found for volunteer with id " + volunteerId, "volunteerId");
        }
        return recommendations;
    }

    @LoggableAction
    public List<Recommendation> getRecommendationsByActivity(Long activityId) {
        List<Recommendation> recommendations = recommendationRepository.findByRecommendationActivity_ActivityId(activityId);
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found for activity with id " + activityId, "activityId");
        }
        return recommendations;
    }

}