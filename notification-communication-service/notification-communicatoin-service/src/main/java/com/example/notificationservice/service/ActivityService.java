package com.example.notificationservice.service;

import com.example.notificationservice.models.Activity;
import com.example.notificationservice.repository.ActivityRepository;

import com.example.notificationservice.models.Organizer;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    public void saveActivity(Activity activity) {
        activityRepository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
    
    public List<Activity> getAllActivities(Pageable pageable) {
        Page<Activity> page = activityRepository.findAll(pageable);
        return page.getContent();
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
                    activity.setTitle(updatedActivity.getTitle());
                    activity.setDescription(updatedActivity.getDescription());
                    activity.setDate(updatedActivity.getDate());
                    activity.setLocation(updatedActivity.getLocation());
                    activity.setOrganizer(updatedActivity.getOrganizer());
                    return activityRepository.save(activity);
                })
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
}
