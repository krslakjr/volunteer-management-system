package com.example.notificationservice.service;

import com.example.notificationservice.exception.VolunteerNotFoundException;
import com.example.notificationservice.models.Volunteer;
import com.example.notificationservice.repository.VolunteerRepository;
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

    public void saveVolunteer(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }    

    public Optional<Volunteer> getVolunteerById(Long id) {
        return Optional.ofNullable(volunteerRepository.findById(id)
                .orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found with ID: " + id)));
    }
    
    

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer updateVolunteer(Long id, Volunteer updatedVolunteer) {
        return volunteerRepository.findById(id)
                .map(volunteer -> {
                    volunteer.setName(updatedVolunteer.getName());
                    volunteer.setEmail(updatedVolunteer.getEmail());
                    volunteer.setPhoneNumber(updatedVolunteer.getPhoneNumber());
                    return volunteerRepository.save(volunteer);
                })
                .orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found with ID: " + id));
    }

    public void deleteVolunteer(Long id) {
        volunteerRepository.findById(id)
                .orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found with ID: " + id));
        volunteerRepository.deleteById(id);
    }
}
