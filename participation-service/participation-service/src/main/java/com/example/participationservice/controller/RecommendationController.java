package com.example.participationservice.controller;

import com.example.participationservice.exception.InvalidPatchException;
import com.example.participationservice.exception.ResourceNotFoundException;
import com.example.participationservice.models.Recommendation;
import com.example.participationservice.service.RecommendationService;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping
    public List<Recommendation> getAllRecommendations() {
        return recommendationService.getAllRecommendations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recommendation> getRecommendationById(@PathVariable Long id) {
        Optional<Recommendation> recommendation = recommendationService.getRecommendationById(id);
        return recommendation.map(ResponseEntity::ok)
                   .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id " + id, "id"));
    }

    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@Valid @RequestBody Recommendation recommendation) {
        Recommendation createdRecommendation = recommendationService.createRecommendation(recommendation);
        return ResponseEntity.ok(createdRecommendation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recommendation> updateRecommendation(@PathVariable Long id,@Valid @RequestBody Recommendation recommendationDetails) {
        Recommendation updatedRecommendation = recommendationService.updateRecommendation(id, recommendationDetails);
        if (updatedRecommendation != null) {
            return ResponseEntity.ok(updatedRecommendation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        if (recommendationService.deleteRecommendation(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRecommendation(@PathVariable Long id, @Valid @RequestBody JsonPatch patch) {
        try {
            Recommendation updatedRecommendation = recommendationService.applyPatchToRecommendation(id, patch);
            return ResponseEntity.ok(updatedRecommendation);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidPatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing patch request.");
        }
    }

    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByVolunteer(@PathVariable Long volunteerId) {
        List<Recommendation> recommendations = recommendationService.getRecommendationsByVolunteer(volunteerId);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByActivity(@PathVariable Long activityId) {
        List<Recommendation> recommendations = recommendationService.getRecommendationsByActivity(activityId);
        return ResponseEntity.ok(recommendations);
    }
}