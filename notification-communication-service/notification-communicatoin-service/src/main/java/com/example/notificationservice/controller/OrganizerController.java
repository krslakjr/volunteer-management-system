package com.example.notificationservice.controller;

import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


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
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable Long id) {
        Optional<Organizer> organizer = organizerService.getOrganizerById(id);
        return organizer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Organizer createOrganizer(@Valid @RequestBody Organizer organizer) {
        return organizerService.createOrganizer(organizer);
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<Organizer> updateOrganizer(@PathVariable Long id, @Valid @RequestBody Organizer updatedOrganizer) {
=======
    public ResponseEntity<Organizer> updateOrganizer(@PathVariable Long id,@Valid @RequestBody Organizer updatedOrganizer) {
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
        try {
            Organizer organizer = organizerService.updateOrganizer(id, updatedOrganizer);
            return ResponseEntity.ok(organizer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
    try {
        organizerService.deleteOrganizer(id);
        return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build(); 
    }
}

}
