package com.example.feedbackservice.service;

import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.feedbackservice.exception.InvalidPatchException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

   public Optional<Activity> getActivityById(Long id) {
    Optional<Activity> activity = activityRepository.findById(id);
  
    if (!activity.isPresent()) {
        throw new ResourceNotFoundException("Activity not found with id " + id, "id");
    }
    return activity;
}

    public Activity saveOrUpdateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
        } else {
            throw new RuntimeException("Activity not found with id " + id);
        }
    }

   
    @Transactional
    public Activity applyPatchToActivity(Long id, JsonPatch patch) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id " + id, "id"));
    
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(activity, JsonNode.class));
            Activity updatedActivity = objectMapper.treeToValue(patched, Activity.class);
    
            if (updatedActivity.getDescription() != null && updatedActivity.getDescription().isEmpty()) {
                throw new InvalidPatchException("Description cannot be empty.");
            }
    
            if (updatedActivity.getVolunteersNeeded() < 0) {
                throw new InvalidPatchException("Volunteers needed cannot be negative.");
            }
    
            updatedActivity.setActivityId(id);
            return activityRepository.save(updatedActivity);
        } catch (JsonPatchException e) {
            throw new InvalidPatchException("Invalid JSON Patch format: " + e.getMessage());
        } catch (IllegalArgumentException | JsonProcessingException e) {
            throw new InvalidPatchException("Error processing JSON Patch: " + e.getMessage());
        }
    }
    
    public Page<Activity> getActivitiesPaginated(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return activityRepository.findAll(pageable);
    }

 public List<Activity> getActivitiesByDescription(String description) {
    return activityRepository.findByDescriptionContaining(description);
}


public List<Activity> getActivitiesByDateAfter(Date date) {
    return activityRepository.findByDateAfter(date);
}


public List<Activity> getActivitiesByLocation(String location) {
    return activityRepository.findByLocationContaining(location);
}


public List<Activity> getActivitiesByVolunteersNeeded(int volunteersNeeded) {
    return activityRepository.findByVolunteersNeededGreaterThanEqual(volunteersNeeded);
}

public List<Activity> getActivitiesByDescriptionAndLocation(String description, String location) {
    return activityRepository.findByDescriptionContainingAndLocationContaining(description, location);
}
    
}