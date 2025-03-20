package com.example.participationservice.service;

import com.example.participationservice.models.Activity;
import com.example.participationservice.repository.ActivityRepository;
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
        return activityRepository.findById(id);
    }

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Long id, Activity activityDetails) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);

        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            activity.setDescription(activityDetails.getDescription());
            activity.setDate(activityDetails.getDate());
            activity.setLocation(activityDetails.getLocation());
            activity.setVolunteersNeeded(activityDetails.getVolunteersNeeded());
            return activityRepository.save(activity);
        }
        return null;
    }

    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
