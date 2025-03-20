package com.example.feedbackservice.service;

import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Get all feedbacks
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Get feedback by ID
    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    // Get feedbacks by activity ID
    public List<Feedback> getFeedbacksByActivityId(Long activityId) {
        return feedbackRepository.findByActivityActivityId(activityId);
    }

    // Get feedbacks by volunteer ID
    public List<Feedback> getFeedbacksByVolunteerId(Long volunteerId) {
        return feedbackRepository.findByVolunteerVolunteerId(volunteerId);
    }

    // Create or update feedback
    public Feedback saveOrUpdateFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // Delete feedback by ID
    public void deleteFeedback(Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
        } else {
            throw new RuntimeException("Feedback not found with id " + id);
        }
    }
}
