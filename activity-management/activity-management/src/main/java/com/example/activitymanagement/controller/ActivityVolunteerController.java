package com.example.activitymanagement.controller;

import com.example.activitymanagement.dto.ActivityVolunteerDTO;
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

    @GetMapping
    public List<ActivityVolunteerDTO> getAllActivityVolunteers() {
        return activityVolunteerService.getAllActivityVolunteers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityVolunteerDTO> getActivityVolunteerById(@PathVariable Long id) {
        Optional<ActivityVolunteerDTO> activityVolunteerDTO = activityVolunteerService.getActivityVolunteerById(id);
        return activityVolunteerDTO.map(volunteerDTO -> new ResponseEntity<>(volunteerDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ActivityVolunteerDTO> createActivityVolunteer(@RequestBody ActivityVolunteerDTO activityVolunteerDTO) {
        try {
            ActivityVolunteerDTO savedActivityVolunteerDTO = activityVolunteerService.createActivityVolunteer(activityVolunteerDTO);
            return new ResponseEntity<>(savedActivityVolunteerDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityVolunteerDTO> updateActivityVolunteer(@PathVariable Long id, @RequestBody ActivityVolunteerDTO activityVolunteerDTO) {
        Optional<ActivityVolunteerDTO> updatedActivityVolunteerDTO = activityVolunteerService.updateActivityVolunteer(id, activityVolunteerDTO);
        return updatedActivityVolunteerDTO.map(volunteerDTO -> new ResponseEntity<>(volunteerDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActivityVolunteer(@PathVariable Long id) {
        boolean isDeleted = activityVolunteerService.deleteActivityVolunteer(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
