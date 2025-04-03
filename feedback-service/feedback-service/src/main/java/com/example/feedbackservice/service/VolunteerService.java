package com.example.feedbackservice.service;


import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.feedbackservice.exception.InvalidPatchException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

 public Optional<Volunteer> getVolunteerById(Long id) {
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        
        if (!volunteer.isPresent()) {
            throw new ResourceNotFoundException("Volunteer not found with id " + id, "id");
        }
        return volunteer;
    }

    public Volunteer saveOrUpdateVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public void deleteVolunteer(Long id) {
        if (volunteerRepository.existsById(id)) {
            volunteerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Volunteer not found with id " + id);
        }
    }

    @Transactional
    public Volunteer applyPatchToVolunteer(Long id, JsonPatch patch) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id " + id, "id"));
    
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(volunteer, JsonNode.class));
            Volunteer updatedVolunteer = objectMapper.treeToValue(patched, Volunteer.class);
    
         
            if (updatedVolunteer.getName() != null && updatedVolunteer.getName().trim().isEmpty()) {
                throw new InvalidPatchException("Name cannot be empty.");
            }
    
            if (updatedVolunteer.getContactInfo() != null && updatedVolunteer.getContactInfo().trim().isEmpty()) {
                throw new InvalidPatchException("Contact info cannot be empty.");
            }
    
            updatedVolunteer.setVolunteerId(id); 
            return volunteerRepository.save(updatedVolunteer);
        } catch (JsonPatchException e) {
            throw new InvalidPatchException("Invalid JSON Patch format: " + e.getMessage());
        } catch (IllegalArgumentException | JsonProcessingException e) {
            throw new InvalidPatchException("Error processing JSON Patch: " + e.getMessage());
        }
    }
    
}