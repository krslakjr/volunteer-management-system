package com.example.feedbackservice.service;

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

    // Get all activities
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // Get activity by ID
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    // Create or update activity
    public Activity saveOrUpdateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    // Delete activity by ID
    public void deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
        } else {
            throw new RuntimeException("Activity not found with id " + id);
        }
    }
}
