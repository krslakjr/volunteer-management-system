package com.example.activitymanagement.controller;

import com.example.activitymanagement.models.ActivityVolunteer;
import com.example.activitymanagement.service.ActivityVolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activity-volunteers")
public class ActivityVolunteerController {

    private final ActivityVolunteerService activityVolunteerService;

    @Autowired
    public ActivityVolunteerController(ActivityVolunteerService activityVolunteerService) {
        this.activityVolunteerService = activityVolunteerService;
    }

    // Get all ActivityVolunteers
    @GetMapping
    public List<ActivityVolunteer> getAllActivityVolunteers() {
        return activityVolunteerService.getAllActivityVolunteers();
    }

    // Get ActivityVolunteer by ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityVolunteer> getActivityVolunteerById(@PathVariable Long id) {
        Optional<ActivityVolunteer> activityVolunteer = activityVolunteerService.getActivityVolunteerById(id);
        return activityVolunteer.map(volunteer -> new ResponseEntity<>(volunteer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new ActivityVolunteer
    @PostMapping
    public ResponseEntity<ActivityVolunteer> createActivityVolunteer(@RequestBody ActivityVolunteer activityVolunteer) {
        try {
            ActivityVolunteer savedActivityVolunteer = activityVolunteerService.createActivityVolunteer(activityVolunteer);
            return new ResponseEntity<>(savedActivityVolunteer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing ActivityVolunteer
    @PutMapping("/{id}")
    public ResponseEntity<ActivityVolunteer> updateActivityVolunteer(@PathVariable Long id, @RequestBody ActivityVolunteer activityVolunteer) {
        Optional<ActivityVolunteer> updatedActivityVolunteer = activityVolunteerService.updateActivityVolunteer(id, activityVolunteer);
        return updatedActivityVolunteer.map(volunteer -> new ResponseEntity<>(volunteer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete an ActivityVolunteer
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActivityVolunteer(@PathVariable Long id) {
        boolean isDeleted = activityVolunteerService.deleteActivityVolunteer(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
