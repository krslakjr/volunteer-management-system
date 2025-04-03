package com.example.feedbackservice.controller;

import com.example.feedbackservice.exception.InvalidPatchException;
import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.service.VolunteerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }



   @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = volunteerService.getVolunteerById(id);
        
        return volunteer.map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id " + id, "id"));
    }


    @PostMapping
    public ResponseEntity<Volunteer> createOrUpdateVolunteer(@Valid @RequestBody Volunteer volunteer) {
        try {
            Volunteer savedVolunteer = volunteerService.saveOrUpdateVolunteer(volunteer);
            return new ResponseEntity<>(savedVolunteer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVolunteer(@PathVariable Long id) {
        try {
            volunteerService.deleteVolunteer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateVolunteer(@PathVariable Long id, @Valid @RequestBody JsonPatch patch) {
        try {
            Volunteer updatedVolunteer = volunteerService.applyPatchToVolunteer(id, patch);
            return ResponseEntity.ok(updatedVolunteer);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidPatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating volunteer.");
        }
    }
    

}