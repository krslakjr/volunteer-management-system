package com.example.feedbackservice.controller;

import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.service.FeedbackService;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.map(f -> new ResponseEntity<>(f, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByActivityId(@PathVariable Long activityId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByActivityId(activityId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByVolunteerId(@PathVariable Long volunteerId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByVolunteerId(volunteerId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Feedback> createOrUpdateFeedback(@Valid @RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.saveOrUpdateFeedback(feedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
