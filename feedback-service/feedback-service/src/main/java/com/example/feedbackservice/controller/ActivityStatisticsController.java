package com.example.feedbackservice.controller;

import com.example.feedbackservice.models.ActivityStatistics;
import com.example.feedbackservice.service.ActivityStatisticsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activity-statistics")
public class ActivityStatisticsController {

    @Autowired
    private ActivityStatisticsService activityStatisticsService;

    @GetMapping
    public ResponseEntity<List<ActivityStatistics>> getAllActivityStatistics() {
        List<ActivityStatistics> statistics = activityStatisticsService.getAllActivityStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityStatistics> getActivityStatisticsById(@PathVariable Long id) {
        Optional<ActivityStatistics> statistics = activityStatisticsService.getActivityStatisticsById(id);
        return statistics.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ActivityStatistics> createOrUpdateActivityStatistics(@Valid @RequestBody ActivityStatistics activityStatistics) {
        try {
            ActivityStatistics savedStatistics = activityStatisticsService.saveOrUpdateActivityStatistics(activityStatistics);
            return new ResponseEntity<>(savedStatistics, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActivityStatistics(@PathVariable Long id) {
        try {
            activityStatisticsService.deleteActivityStatistics(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
