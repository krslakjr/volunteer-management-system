package com.example.feedbackservice.service;

import com.example.feedbackservice.repository.FeedbackRepository;
import com.example.feedbackservice.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.feedbackservice.repository.ActivityRepository;
import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.exception.InvalidPatchException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.models.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ActivityRepository activityRepository; 

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if (!feedback.isPresent()) {
            throw new ResourceNotFoundException("Feedback not found with id " + id, "id");
        }
        return feedback;
    }

    public List<Feedback> getFeedbacksByActivityId(Long activityId) {
        return feedbackRepository.findByActivityActivityId(activityId);
    }

    public List<Feedback> getFeedbacksByVolunteerId(Long volunteerId) {
        return feedbackRepository.findByVolunteerVolunteerId(volunteerId);
    }

    public Feedback saveOrUpdateFeedback(Feedback feedback) {
        Optional<Volunteer> volunteer = volunteerRepository.findById(feedback.getVolunteer().getVolunteerId());
        Optional<Activity> activity = activityRepository.findById(feedback.getActivity().getActivityId());
    
        if (volunteer.isPresent() && activity.isPresent()) {
            feedback.setVolunteer(volunteer.get());
            feedback.setActivity(activity.get());
            return feedbackRepository.save(feedback);
        } else {
            
            throw new ResourceNotFoundException("Invalid Volunteer or Activity ID", "volunteerId or activityId");
        }
    }
    
    public void deleteFeedback(Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if (feedback.isPresent()) {
            feedbackRepository.deleteById(id);
        } else {
            
            throw new ResourceNotFoundException("Feedback not found with id " + id, "id");
        }
    }

    @Transactional
public Feedback applyPatchToFeedback(Long id, JsonPatch patch) {
    Feedback feedback = feedbackRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + id, "id"));

    try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(feedback, JsonNode.class));
        Feedback updatedFeedback = objectMapper.treeToValue(patched, Feedback.class);

        if (updatedFeedback.getRating() < 1 || updatedFeedback.getRating() > 5) {
            throw new InvalidPatchException("Rating must be between 1 and 5.");
        }

        if (updatedFeedback.getComment() != null && updatedFeedback.getComment().trim().isEmpty()) {
            throw new InvalidPatchException("Comment cannot be empty.");
        }

        updatedFeedback.setFeedbackId(id); 
        return feedbackRepository.save(updatedFeedback);
    } catch (JsonPatchException e) {
        throw new InvalidPatchException("Invalid JSON Patch format: " + e.getMessage());
    } catch (IllegalArgumentException | JsonProcessingException e) {
        throw new InvalidPatchException("Error processing JSON Patch: " + e.getMessage());
    }
}


}