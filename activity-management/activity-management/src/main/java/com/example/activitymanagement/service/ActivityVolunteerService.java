package com.example.activitymanagement.service;

import com.example.activitymanagement.models.ActivityVolunteer;
import com.example.activitymanagement.repository.ActivityVolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityVolunteerService {

    private final ActivityVolunteerRepository activityVolunteerRepository;

    @Autowired
    public ActivityVolunteerService(ActivityVolunteerRepository activityVolunteerRepository) {
        this.activityVolunteerRepository = activityVolunteerRepository;
    }

    // Get all ActivityVolunteers
    public List<ActivityVolunteer> getAllActivityVolunteers() {
        return activityVolunteerRepository.findAll();
    }

    // Get ActivityVolunteer by ID
    public Optional<ActivityVolunteer> getActivityVolunteerById(Long id) {
        return activityVolunteerRepository.findById(id);
    }

    // Create a new ActivityVolunteer
    public ActivityVolunteer createActivityVolunteer(ActivityVolunteer activityVolunteer) {
        return activityVolunteerRepository.save(activityVolunteer);
    }

    // Update an existing ActivityVolunteer
    public Optional<ActivityVolunteer> updateActivityVolunteer(Long id, ActivityVolunteer activityVolunteer) {
        return activityVolunteerRepository.findById(id)
                .map(existingActivityVolunteer -> {
                    existingActivityVolunteer.setActivity(activityVolunteer.getActivity());
                    existingActivityVolunteer.setVolunteer(activityVolunteer.getVolunteer());
                    return activityVolunteerRepository.save(existingActivityVolunteer);
                });
    }

    // Delete an ActivityVolunteer
    public boolean deleteActivityVolunteer(Long id) {
        return activityVolunteerRepository.findById(id)
                .map(existingActivityVolunteer -> {
                    activityVolunteerRepository.delete(existingActivityVolunteer);
                    return true;
                })
                .orElse(false);
    }
}
