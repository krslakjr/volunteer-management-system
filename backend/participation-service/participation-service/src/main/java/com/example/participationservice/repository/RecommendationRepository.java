package com.example.participationservice.repository;

import com.example.participationservice.models.Recommendation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    List<Recommendation> findByVolunteer_VolunteerId(Long volunteerId);

    List<Recommendation> findByRecommendationActivity_ActivityId(Long activityId);
}
