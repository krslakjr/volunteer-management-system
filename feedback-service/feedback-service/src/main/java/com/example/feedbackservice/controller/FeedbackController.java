package com.example.feedbackservice.controller;

import com.example.feedbackservice.exception.InvalidPatchException;
import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.service.ActivityClientService;
import com.example.feedbackservice.service.UserClientService;
import org.springframework.web.client.RestTemplate;
import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    private final UserClientService userClientService;
    private final ActivityClientService activityClientService;

    public FeedbackController(FeedbackService feedbackService,
                              UserClientService userClientService,
                              ActivityClientService activityClientService) {
        this.feedbackService = feedbackService;
        this.userClientService = userClientService;
        this.activityClientService = activityClientService;
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/load-test")
    public Map<String, Integer> testUserServiceLoadBalancing() {
        Map<String, Integer> stats = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            String response = restTemplate.getForObject("http://user-service/api/test", String.class);
            stats.put(response, stats.getOrDefault(response, 0) + 1);
        }

        return stats;
    }

    @GetMapping("/test-loadbalancer")
public ResponseEntity<Map<String, Integer>> testLoadBalancer() {
    Map<String, Integer> hitCount = new HashMap<>();
    long start = System.currentTimeMillis();

    for (int i = 0; i < 100; i++) {
        try {
            String result = restTemplate.getForObject("http://user-service/users/test", String.class);
            hitCount.put(result, hitCount.getOrDefault(result, 0) + 1);
        } catch (Exception e) {
            hitCount.put("ERROR", hitCount.getOrDefault("ERROR", 0) + 1);
        }
    }

    long duration = System.currentTimeMillis() - start;
    hitCount.put("TotalTime(ms)", (int) duration);
    return ResponseEntity.ok(hitCount);
}

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
    public ResponseEntity<?> createOrUpdateFeedback(@Valid @RequestBody Feedback feedback) {
        try {
            Long userId = feedback.getVolunteer().getVolunteerId();
            Long activityId = feedback.getActivity().getActivityId();
            if (!userClientService.isValidVolunteer(userId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User must exist and have role Volunteer");
            }
            activityClientService.doesActivityExist(activityId);
            Feedback savedFeedback = feedbackService.saveOrUpdateFeedback(feedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getReason());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFeedback(@Valid @PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   

    @PatchMapping("/{id}")
public ResponseEntity<?> updateFeedback(@PathVariable Long id, @Valid @RequestBody JsonPatch patch) {
    try {
        Feedback updatedFeedback = feedbackService.applyPatchToFeedback(id, patch);
        return ResponseEntity.ok(updatedFeedback);
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (InvalidPatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating feedback.");
    }
}


}