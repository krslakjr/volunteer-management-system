package com.example.userservice.service;

import com.example.userservice.exception.ResourceNotFoundException;
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

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Long id, Activity updatedActivity) {
        return activityRepository.findById(id)
                .map(activity -> {
                    activity.setActivityName(updatedActivity.getActivityName());
                    activity.setDescription(updatedActivity.getDescription());
                    activity.setActivityDate(updatedActivity.getActivityDate());
                    activity.setOrganizer(updatedActivity.getOrganizer());
                    return activityRepository.save(activity);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id " + id, "id"));
    }
    
    
    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activity not found with id " + id, "id");
        }
        activityRepository.deleteById(id);
    }
}