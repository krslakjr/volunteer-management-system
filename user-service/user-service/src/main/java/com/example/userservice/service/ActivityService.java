package com.example.userservice.service;

import com.example.userservice.models.Activity;
import com.example.userservice.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // Get all Activities
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // Get Activity by ID
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    // Create a new Activity
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    // Update an existing Activity
    public Activity updateActivity(Long id, Activity activity) {
        if (activityRepository.existsById(id)) {
            return activityRepository.save(activity);
        }
        return null;  // Return null or throw exception if not found
    }

    // Delete an Activity
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
}
