package com.example.activitymanagement.controller;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.exception.ResourceNotFoundException;
import com.example.activitymanagement.mapper.ActivityMapper;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.repository.ActivityRepository;
import com.example.activitymanagement.service.ActivityService;
import com.example.activitymanagement.service.JsonPatchHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    private final JsonPatchHelper jsonPatchHelper;
    private final ActivityRepository activityRepository;

    public ActivityController(ActivityService activityService, ActivityMapper activityMapper, JsonPatchHelper jsonPatchHelper, ActivityRepository activityRepository) {
        this.activityService = activityService;
        this.activityMapper = activityMapper;
        this.jsonPatchHelper = jsonPatchHelper;
        this.activityRepository = activityRepository;
    }

    @GetMapping
    public ResponseEntity<Page<ActivityDTO>> getAllActivities(Pageable pageable) {
        Page<Activity> activityPage = activityRepository.findAll(pageable);
        Page<ActivityDTO> activityDTOPage = activityPage.map(activityMapper::toActivityDTO);
        return new ResponseEntity<>(activityDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id)
            .map(activity -> ResponseEntity.ok(activity))
            .orElseThrow(() -> new ResourceNotFoundException("Activity not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity createActivity(@Valid @RequestBody ActivityDTO activityDTO) {
        return activityMapper.toActivity(activityService.createActivity(activityDTO));
    }

    @PostMapping("/batch")
    public List<Activity> createActivities(@Valid @RequestBody List<Activity> activities) {
        return activityService.saveAll(activities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @Valid @RequestBody Activity updatedActivity) {
        ActivityDTO updatedDTO = activityService.updateActivity(id, activityMapper.toActivityDTO(updatedActivity));
        if (updatedDTO == null) {
            throw new ResourceNotFoundException("Activity not found");
        }
        return ResponseEntity.ok(activityMapper.toActivity(updatedDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody JsonPatch patch) {
        Optional<ActivityDTO> existingActivityOpt = activityService.getActivityById(id);

        if (existingActivityOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            ActivityDTO existingActivity = existingActivityOpt.get();
            ActivityDTO patchedActivity = jsonPatchHelper.applyPatch(patch, existingActivity, ActivityDTO.class);

            ActivityDTO savedActivity = activityService.updateActivity(id, patchedActivity);
            return ResponseEntity.ok(activityMapper.toActivity(savedActivity));
        } catch (JsonPatchException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
