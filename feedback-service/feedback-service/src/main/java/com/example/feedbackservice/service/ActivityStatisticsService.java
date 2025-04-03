package com.example.feedbackservice.service;

import com.github.fge.jsonpatch.JsonPatchException;
import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.models.ActivityStatistics;
import com.example.feedbackservice.repository.ActivityStatisticsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.feedbackservice.exception.InvalidPatchException;
import java.util.Optional;
import java.util.List;

@Service
public class ActivityStatisticsService {

    @Autowired
    private ActivityStatisticsRepository activityStatisticsRepository;
    
    public List<ActivityStatistics> getAllActivityStatistics() {
        return activityStatisticsRepository.findAll();
    }

     public Optional<ActivityStatistics> getActivityStatisticsById(Long id) {
        Optional<ActivityStatistics> statistics = activityStatisticsRepository.findById(id);
      
        if (!statistics.isPresent()) {
            throw new ResourceNotFoundException("Activity Statistics not found with id " + id, "id");
        }
        return statistics;
    }

    public ActivityStatistics saveOrUpdateActivityStatistics(ActivityStatistics activityStatistics) {
        return activityStatisticsRepository.save(activityStatistics);
    }

    public void deleteActivityStatistics(Long id) {
        if (activityStatisticsRepository.existsById(id)) {
            activityStatisticsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Activity Statistics not found with id " + id);
        }
    }

    @Transactional
    public ActivityStatistics applyPatchToActivityStatistics(Long id, JsonPatch patch) {
        ActivityStatistics activityStatistics = activityStatisticsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity statistics not found with id " + id, "id"));
    
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(activityStatistics, JsonNode.class));
            ActivityStatistics updatedActivityStatistics = objectMapper.treeToValue(patched, ActivityStatistics.class);
    
            if (updatedActivityStatistics.getAverageRating() < 0 || updatedActivityStatistics.getAverageRating() > 5) {
                throw new InvalidPatchException("Average rating must be between 0 and 5.");
            }
    
            if (updatedActivityStatistics.getTotalRatings() < 0) {
                throw new InvalidPatchException("Total ratings cannot be negative.");
            }
    
            if (updatedActivityStatistics.getTotalComments() < 0) {
                throw new InvalidPatchException("Total comments cannot be negative.");
            }
    
            updatedActivityStatistics.setId(id);
            return activityStatisticsRepository.save(updatedActivityStatistics);
        } catch (JsonPatchException e) {
            throw new InvalidPatchException("Invalid JSON Patch format: " + e.getMessage());
        } catch (IllegalArgumentException | JsonProcessingException e) {
            throw new InvalidPatchException("Error processing JSON Patch: " + e.getMessage());
        }
    }
    
    
}