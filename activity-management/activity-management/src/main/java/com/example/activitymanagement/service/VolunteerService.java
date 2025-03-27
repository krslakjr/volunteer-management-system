package com.example.activitymanagement.service;

import com.example.activitymanagement.models.Volunteer;
import com.example.activitymanagement.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer updateVolunteer(Long id, Volunteer updatedVolunteer) {
        return volunteerRepository.findById(id)
                .map(volunteer -> {
                    volunteer.setName(updatedVolunteer.getName());
                    volunteer.setContactInfo(updatedVolunteer.getContactInfo());
                    return volunteerRepository.save(volunteer);
                })
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));
    }

    public void deleteVolunteer(Long id) {
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        if (volunteer.isPresent()) {
            volunteerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Volunteer not found");
        }
    }
    
}
