package com.example.participationservice.service;

import com.example.participationservice.models.Recommendation;
import com.example.participationservice.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    public Optional<Recommendation> getRecommendationById(Long id) {
        return recommendationRepository.findById(id);
    }

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
}
