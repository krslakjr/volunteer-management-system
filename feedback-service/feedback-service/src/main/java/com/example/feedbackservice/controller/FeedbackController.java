package com.example.feedbackservice.controller;

import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

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
public ResponseEntity<Object> getFeedbackById(@PathVariable Long id) {
    try {
        Feedback feedback = feedbackService.getFeedbackById(id)
                                          .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + id, "id"));
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    } catch (ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
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
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}