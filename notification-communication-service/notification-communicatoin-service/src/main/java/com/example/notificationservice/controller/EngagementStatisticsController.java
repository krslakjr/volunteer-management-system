package com.example.notificationservice.controller;

import com.example.notificationservice.models.EngagementStatistics;
import com.example.notificationservice.service.EngagementStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.example.notificationservice.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/engagement-statistics")
public class EngagementStatisticsController {

    private final EngagementStatisticsService engagementStatisticsService;

    public EngagementStatisticsController(EngagementStatisticsService engagementStatisticsService) {
        this.engagementStatisticsService = engagementStatisticsService;
    }

        @GetMapping
    public List<EngagementStatistics> getAllStatistics(@RequestParam(required = false) Integer page, 
                                            @RequestParam(required = false) Integer size) {
        Pageable pageable = Pageable.unpaged();
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size);
        }
        return engagementStatisticsService.getAllStatistics(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngagementStatistics> getStatisticsById(@PathVariable Long id) {
        Optional<EngagementStatistics> statistics = engagementStatisticsService.getStatisticsById(id);
        return statistics.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement Statistics not found with id " + id, "id"));
    }

    @PostMapping
    public EngagementStatistics createStatistics(@Valid @RequestBody EngagementStatistics statistics) {
        return engagementStatisticsService.createStatistics(statistics);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EngagementStatistics> updateStatistics(@PathVariable Long id,@Valid @RequestBody EngagementStatistics updatedStatistics) {
        try {
            EngagementStatistics statistics = engagementStatisticsService.updateStatistics(id, updatedStatistics);
            return ResponseEntity.ok(statistics);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Long id) {
        engagementStatisticsService.deleteStatistics(id);
        return ResponseEntity.noContent().build();
    }
}