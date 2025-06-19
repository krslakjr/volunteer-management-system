package com.example.feedbackservice.controller;

import com.example.feedbackservice.exception.ResourceNotFoundException;
import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.feedbackservice.exception.InvalidPatchException;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityService.getActivityById(id);
       
        return activity.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id " + id, "id"));
    }

    @PostMapping
    public ResponseEntity<Activity> createOrUpdateActivity(@Valid @RequestBody Activity activity) {
        try {
            Activity savedActivity = activityService.saveOrUpdateActivity(activity);
            return new ResponseEntity<>(savedActivity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable Long id) {
        try {
            activityService.deleteActivity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Long id, @Valid @RequestBody JsonPatch patch) {
        try {
            Activity updatedActivity = activityService.applyPatchToActivity(id, patch);
            return ResponseEntity.ok(updatedActivity);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidPatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating activity.");
        }
    }
    

    @GetMapping("/paginated")
    public ResponseEntity<Page<Activity>> getActivitiesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        
        Page<Activity> activities = activityService.getActivitiesPaginated(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(activities);
    }

    

    @GetMapping("/filter/description")
    public ResponseEntity<List<Activity>> getActivitiesByDescription(@RequestParam String description) {
        List<Activity> activities = activityService.getActivitiesByDescription(description);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

   
    @GetMapping("/filter/date")
    public ResponseEntity<List<Activity>> getActivitiesByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Activity> activities = activityService.getActivitiesByDateAfter(date);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/filter/location")
    public ResponseEntity<List<Activity>> getActivitiesByLocation(@RequestParam String location) {
        List<Activity> activities = activityService.getActivitiesByLocation(location);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

   
    @GetMapping("/filter/volunteers")
    public ResponseEntity<List<Activity>> getActivitiesByVolunteers(@RequestParam int volunteersNeeded) {
        List<Activity> activities = activityService.getActivitiesByVolunteersNeeded(volunteersNeeded);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }


    @GetMapping("/filter/description-and-location")
    public ResponseEntity<List<Activity>> getActivitiesByDescriptionAndLocation(@RequestParam String description, @RequestParam String location) {
        List<Activity> activities = activityService.getActivitiesByDescriptionAndLocation(description, location);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
}