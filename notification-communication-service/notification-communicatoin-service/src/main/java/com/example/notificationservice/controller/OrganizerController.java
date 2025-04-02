package com.example.notificationservice.controller;

import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.service.OrganizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping
    public List<Organizer> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrganizerById(@PathVariable Long id) {
        Optional<Organizer> organizer = organizerService.getOrganizerById(id);
        if (organizer.isPresent()) {
            return ResponseEntity.ok(organizer.get());
        } else {
           
            return ResponseEntity.status(404).body("Organizer not found with ID " + id);
        }
    }

    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(@RequestBody Organizer organizer) {
        Organizer createdOrganizer = organizerService.createOrganizer(organizer);
        return ResponseEntity.status(201).body(createdOrganizer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrganizer(@PathVariable Long id, @RequestBody Organizer updatedOrganizer) {
        try {
            Organizer organizer = organizerService.updateOrganizer(id, updatedOrganizer);
            return ResponseEntity.ok(organizer);
        } catch (RuntimeException e) {
         
            return ResponseEntity.status(404).body("Organizer not found with ID " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
        try {
            organizerService.deleteOrganizer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
           
            return ResponseEntity.status(404).build();
        }
    }
}