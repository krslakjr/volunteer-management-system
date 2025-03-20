package com.example.feedbackservice.controller;

import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    // Get all volunteers
    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }

    // Get volunteer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = volunteerService.getVolunteerById(id);
        return volunteer.map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create or update volunteer
    @PostMapping
    public ResponseEntity<Volunteer> createOrUpdateVolunteer(@RequestBody Volunteer volunteer) {
        try {
            Volunteer savedVolunteer = volunteerService.saveOrUpdateVolunteer(volunteer);
            return new ResponseEntity<>(savedVolunteer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete volunteer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVolunteer(@PathVariable Long id) {
        try {
            volunteerService.deleteVolunteer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
