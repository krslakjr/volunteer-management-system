package com.example.activitymanagement.service;

import com.example.activitymanagement.dto.ActivityVolunteerDTO;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import com.example.activitymanagement.mapper.ActivityVolunteerMapper;
import com.example.activitymanagement.models.ActivityVolunteer;
import com.example.activitymanagement.repository.ActivityVolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityVolunteerService {

    private final ActivityVolunteerRepository activityVolunteerRepository;
    private final ActivityVolunteerMapper activityVolunteerMapper;

    @Autowired
    public ActivityVolunteerService(ActivityVolunteerRepository activityVolunteerRepository, ActivityVolunteerMapper activityVolunteerMapper) {
        this.activityVolunteerRepository = activityVolunteerRepository;
        this.activityVolunteerMapper = activityVolunteerMapper;
    }

    public List<ActivityVolunteerDTO> getAllActivityVolunteers() {
        return activityVolunteerRepository.findAll().stream()
                .map(activityVolunteerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ActivityVolunteerDTO> getActivityVolunteerById(Long id) {
        return activityVolunteerRepository.findById(id)
                .map(activityVolunteerMapper::toDTO);
    }
    
    public ActivityVolunteerDTO createActivityVolunteer(ActivityVolunteerDTO activityVolunteerDTO) {
        ActivityVolunteer activityVolunteer = activityVolunteerMapper.toEntity(activityVolunteerDTO); 
        ActivityVolunteer savedActivityVolunteer = activityVolunteerRepository.save(activityVolunteer);
        return activityVolunteerMapper.toDTO(savedActivityVolunteer); 
    }

    public Optional<ActivityVolunteerDTO> updateActivityVolunteer(Long id, ActivityVolunteerDTO activityVolunteerDTO) {
        return activityVolunteerRepository.findById(id)
                .map(existingActivityVolunteer -> {
                    existingActivityVolunteer.setActivity(activityVolunteerMapper.toEntity(activityVolunteerDTO).getActivity());
                    existingActivityVolunteer.setVolunteer(activityVolunteerMapper.toEntity(activityVolunteerDTO).getVolunteer());
                    ActivityVolunteer updatedActivityVolunteer = activityVolunteerRepository.save(existingActivityVolunteer);
                    return activityVolunteerMapper.toDTO(updatedActivityVolunteer);
                });
    }

    public boolean deleteActivityVolunteer(Long id) {
        return activityVolunteerRepository.findById(id)
                .map(existingActivityVolunteer -> {
                    activityVolunteerRepository.delete(existingActivityVolunteer);
                    return true;
                })
                .orElse(false);
    }
}