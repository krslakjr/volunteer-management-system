package com.example.notificationservice.controller;

import com.example.notificationservice.models.EngagementStatistics;
import com.example.notificationservice.service.EngagementStatisticsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/engagement-statistics")
public class EngagementStatisticsController {

    private final EngagementStatisticsService engagementStatisticsService;

    public EngagementStatisticsController(EngagementStatisticsService engagementStatisticsService) {
        this.engagementStatisticsService = engagementStatisticsService;
    }

    @GetMapping
    public List<EngagementStatistics> getAllStatistics() {
        return engagementStatisticsService.getAllStatistics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngagementStatistics> getStatisticsById(@PathVariable Long id) {
        Optional<EngagementStatistics> statistics = engagementStatisticsService.getStatisticsById(id);
        return statistics.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EngagementStatistics createStatistics(@Valid @RequestBody EngagementStatistics statistics) {
        return engagementStatisticsService.createStatistics(statistics);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EngagementStatistics> updateStatistics(@PathVariable Long id, @Valid @RequestBody EngagementStatistics updatedStatistics) {
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
