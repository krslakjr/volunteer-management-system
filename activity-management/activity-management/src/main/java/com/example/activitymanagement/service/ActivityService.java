package com.example.activitymanagement.service;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import com.example.activitymanagement.exception.ValidationException;
import com.example.activitymanagement.mapper.ActivityMapper;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityService(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activityMapper::toActivityDTO)
                .collect(Collectors.toList());
    }

    public Optional<ActivityDTO> getActivityById(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new ResourceNotFoundException("Activity with ID " + id + " not found");
        }
        return activity.map(activityMapper::toActivityDTO);
    }

    public ActivityDTO createActivity(Activity activity) {
        if (activity.getDescription() == null || activity.getDescription().length() < 10) {
            throw new ValidationException("Description must be at least 10 characters", "description");
        }
        Activity savedActivity = activityRepository.save(activity);
        return activityMapper.toActivityDTO(savedActivity);
    }

    public ActivityDTO updateActivity(Long id, Activity updatedActivity) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity with ID " + id + " not found"));

        existingActivity.setDescription(updatedActivity.getDescription());
        existingActivity.setDate(updatedActivity.getDate());
        existingActivity.setLocation(updatedActivity.getLocation());
        existingActivity.setVolunteersNeeded(updatedActivity.getVolunteersNeeded());

        Activity savedActivity = activityRepository.save(existingActivity);
        return activityMapper.toActivityDTO(savedActivity);
    }

    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activity with ID " + id + " not found");
        }
        activityRepository.deleteById(id);
    }
}
