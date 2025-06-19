package com.example.feedbackservice.repository;

import com.example.feedbackservice.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByActivityActivityId(Long activityId);
    List<Feedback> findByVolunteerVolunteerId(Long volunteerId);
}
