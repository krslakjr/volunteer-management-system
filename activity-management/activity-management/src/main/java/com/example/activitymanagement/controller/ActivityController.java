package com.example.activitymanagement.controller;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.service.ActivityService;
<<<<<<< HEAD
import jakarta.validation.Valid;
=======

import org.springframework.http.HttpStatus;
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<ActivityDTO> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id)
            .map(activity -> ResponseEntity.ok(activity))  
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
<<<<<<< HEAD
    public Activity createActivity(@Valid @RequestBody Activity activity) {
        return activityService.createActivity(activity);
=======
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody Activity activity) {
        ActivityDTO createdActivityDTO = activityService.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivityDTO);
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @Valid @RequestBody Activity updatedActivity) {
        return ResponseEntity.ok(activityService.updateActivity(id, updatedActivity));
=======
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id,@Valid @RequestBody Activity updatedActivity) {
        ActivityDTO updatedActivityDTO = activityService.updateActivity(id, updatedActivity);
        return ResponseEntity.ok(updatedActivityDTO); 
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
