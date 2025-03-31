package com.example.participationservice.service;

import com.example.participationservice.exception.VolunteerNotFoundException;
import com.example.participationservice.exception.ResourceNotFoundException;
import com.example.participationservice.models.Volunteer;
import com.example.participationservice.repository.VolunteerRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }
     public void saveVolunteer(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }

 
    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id); 
    }
    

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer updateVolunteer(Long id, Volunteer volunteerDetails) {
        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(id);

        if (optionalVolunteer.isPresent()) {
            Volunteer volunteer = optionalVolunteer.get();
            volunteer.setName(volunteerDetails.getName());
            volunteer.setContactInfo(volunteerDetails.getContactInfo());
            return volunteerRepository.save(volunteer);
        }
        return null;
    }

    public boolean deleteVolunteer(Long id) {
        if (volunteerRepository.existsById(id)) {
            volunteerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}