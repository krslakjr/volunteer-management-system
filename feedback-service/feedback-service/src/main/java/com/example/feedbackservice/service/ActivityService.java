package com.example.feedbackservice.service;

import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}