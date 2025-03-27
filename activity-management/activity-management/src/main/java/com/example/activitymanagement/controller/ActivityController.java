package com.example.activitymanagement.controller;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.service.ActivityService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody Activity activity) {
        ActivityDTO createdActivityDTO = activityService.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @RequestBody Activity updatedActivity) {
        ActivityDTO updatedActivityDTO = activityService.updateActivity(id, updatedActivity);
        return ResponseEntity.ok(updatedActivityDTO); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
