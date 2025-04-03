package com.example.notificationservice.service;

import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.repository.OrganizerRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    public List<Organizer> getAllOrganizers(Pageable pageable) {
        Page<Organizer> page = organizerRepository.findAll(pageable);
        return page.getContent();
    }
    
    public Optional<Organizer> getOrganizerById(Long id) {
        return organizerRepository.findById(id);
    }

    public List<Organizer> getOrganizerByName(String name) {
        return organizerRepository.findByName(name);
    }

    @Transactional
    public Organizer createOrganizer(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Transactional
    public Organizer updateOrganizer(Long id, Organizer updatedOrganizer) {
        return organizerRepository.findById(id)
                .map(organizer -> {
                    organizer.setName(updatedOrganizer.getName());
                    organizer.setEmail(updatedOrganizer.getEmail());
                    organizer.setPhoneNumber(updatedOrganizer.getPhoneNumber());
                    return organizerRepository.save(organizer);
                })
                .orElseThrow(() -> new RuntimeException("Organizer not found with ID " + id));
    }

    public void saveOrganizer(Organizer organizer) {
        organizerRepository.save(organizer);
    }

    @Transactional
    public void deleteOrganizer(Long id) {
        if (organizerRepository.existsById(id)) {
            organizerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Organizer not found with ID " + id);
        }
    }
}