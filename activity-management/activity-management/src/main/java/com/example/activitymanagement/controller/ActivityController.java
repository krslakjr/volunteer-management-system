package com.example.activitymanagement.controller;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<?> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

  @GetMapping("/{id}")
public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
    ActivityDTO activityDTO = activityService.getActivityById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
    return ResponseEntity.ok(activityDTO);
}


    @PostMapping
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody Activity activity) {
        ActivityDTO createdActivityDTO = activityService.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @Valid @RequestBody Activity updatedActivity) {
        ActivityDTO updatedActivityDTO = activityService.updateActivity(id, updatedActivity);
        return ResponseEntity.ok(updatedActivityDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
