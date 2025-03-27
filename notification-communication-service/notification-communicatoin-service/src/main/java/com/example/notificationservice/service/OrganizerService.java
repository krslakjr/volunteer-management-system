package com.example.notificationservice.service;

import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.repository.OrganizerRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepository;

    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    public Optional<Organizer> getOrganizerById(Long id) {
        return organizerRepository.findById(id);
    }

    public Organizer createOrganizer(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    public Organizer updateOrganizer(Long id, Organizer updatedOrganizer) {
        return organizerRepository.findById(id)
                .map(organizer -> {
                    organizer.setName(updatedOrganizer.getName());
                    organizer.setEmail(updatedOrganizer.getEmail());
                    organizer.setPhoneNumber(updatedOrganizer.getPhoneNumber());
                    return organizerRepository.save(organizer);
                })
                .orElseThrow(() -> new RuntimeException("Organizer not found"));
    }

    public void saveOrganizer(Organizer organizer) {
        organizerRepository.save(organizer);
    }

    public void deleteOrganizer(Long id) {
        Optional<Organizer> organizer = organizerRepository.findById(id);
        if (organizer.isPresent()) {
            organizerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Organizer not found with ID " + id); 
        }
    }
    
}
