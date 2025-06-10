package com.example.activitymanagement.service;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import com.example.activitymanagement.exception.ValidationException;
import com.example.activitymanagement.logging.LoggableAction;
import com.example.activitymanagement.mapper.ActivityMapper;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.repository.ActivityRepository;
import jakarta.transaction.Transactional;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.domain.Pageable;
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
        return activity.map(activityMapper::toActivityDTO);
    }

    @LoggableAction
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        if (activityDTO.getDescription() == null || activityDTO.getDescription().trim().length() < 10) {
            throw new ValidationException("Description must be at least 10 characters", "description");
        }
    
        Activity activity = activityMapper.toActivity(activityDTO);
        Activity savedActivity = activityRepository.save(activity);
        return activityMapper.toActivityDTO(savedActivity);
    }
    
    @LoggableAction
    public ActivityDTO updateActivity(Long id, ActivityDTO updatedActivityDTO) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity with ID " + id + " not found"));
    
        existingActivity.setDescription(updatedActivityDTO.getDescription());
        existingActivity.setDate(updatedActivityDTO.getDate());
        existingActivity.setLocation(updatedActivityDTO.getLocation());
        existingActivity.setVolunteersNeeded(updatedActivityDTO.getVolunteersNeeded());
    
        Activity savedActivity = activityRepository.save(existingActivity);
        return activityMapper.toActivityDTO(savedActivity);
    }
    
    

    @Transactional
    public List<Activity> saveAll(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    @LoggableAction
    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activity with ID " + id + " not found");
        }
        activityRepository.deleteById(id);
    }
}