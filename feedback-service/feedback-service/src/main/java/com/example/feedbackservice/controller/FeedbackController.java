package com.example.feedbackservice.controller;

import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Get all feedbacks
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.map(f -> new ResponseEntity<>(f, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get feedbacks by activity ID
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByActivityId(@PathVariable Long activityId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByActivityId(activityId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedbacks by volunteer ID
    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByVolunteerId(@PathVariable Long volunteerId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByVolunteerId(volunteerId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Create or update feedback
    @PostMapping
    public ResponseEntity<Feedback> createOrUpdateFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.saveOrUpdateFeedback(feedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete feedback by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
