package com.example.activitymanagement.controller;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.mapper.ActivityMapper; 
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityMapper activityMapper;

    public ActivityController(ActivityService activityService, ActivityMapper activityMapper) {
        this.activityService = activityService;
        this.activityMapper = activityMapper;
    }

    @GetMapping
    public List<ActivityDTO> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id)
            .map(activity -> ResponseEntity.ok(activityMapper.toActivityDTO(activity)))  
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Activity createActivity(@Valid @RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @Valid @RequestBody Activity updatedActivity) {
        return ResponseEntity.ok(activityService.updateActivity(id, updatedActivity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
