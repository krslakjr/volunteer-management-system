package com.example.notificationservice.controller;

import com.example.notificationservice.models.Volunteer;
import com.example.notificationservice.service.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerService.getAllVolunteers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = volunteerService.getVolunteerById(id);
        return volunteer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @PutMapping("/{id}")
public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Long id, @RequestBody Volunteer updatedVolunteer) {
    try {
        Volunteer volunteer = volunteerService.updateVolunteer(id, updatedVolunteer);
        return ResponseEntity.ok(volunteer);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}


@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
    try {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
}
