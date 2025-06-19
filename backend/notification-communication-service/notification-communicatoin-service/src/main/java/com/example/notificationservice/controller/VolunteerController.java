package com.example.notificationservice.controller;

import com.example.notificationservice.exception.VolunteerNotFoundException;
import com.example.notificationservice.models.Volunteer;
import com.example.notificationservice.service.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping
    public List<Volunteer> getAllVolunteers(@RequestParam(required = false) Integer page, 
                                            @RequestParam(required = false) Integer size) {
        Pageable pageable = Pageable.unpaged();
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size);
        }
        return volunteerService.getAllVolunteers(pageable);
    }

    @GetMapping("/search")
public List<Volunteer> searchVolunteers(@RequestParam String name) {
    return volunteerService.getVolunteersByName(name);
}


    @GetMapping("/{id}")
    public ResponseEntity<?> getVolunteerById(@PathVariable Long id) {
        try {
            Volunteer volunteer = volunteerService.getVolunteerById(id).orElseThrow();
            return ResponseEntity.ok(volunteer);
        } catch (VolunteerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    

    @PostMapping
    public Volunteer createVolunteer(@Valid @RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Long id, @Valid @RequestBody Volunteer updatedVolunteer) {
        try {
            Volunteer volunteer = volunteerService.updateVolunteer(id, updatedVolunteer);
            return ResponseEntity.ok(volunteer);
        } catch (VolunteerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        try {
            volunteerService.deleteVolunteer(id);
            return ResponseEntity.noContent().build();
        } catch (VolunteerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}