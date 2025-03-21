package com.example.notificationservice.service;

import com.example.notificationservice.models.EngagementStatistics;
import com.example.notificationservice.repository.EngagementStatisticsRepository;

import com.example.notificationservice.models.Organizer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EngagementStatisticsService {

    private final EngagementStatisticsRepository engagementStatisticsRepository;

    public EngagementStatisticsService(EngagementStatisticsRepository engagementStatisticsRepository) {
        this.engagementStatisticsRepository = engagementStatisticsRepository;
    }

    public void saveEngagementStatistics(EngagementStatistics engagementStatistics) {
        engagementStatisticsRepository.save(engagementStatistics);
    }
    public List<EngagementStatistics> getAllStatistics() {
        return engagementStatisticsRepository.findAll();
    }

    public Optional<EngagementStatistics> getStatisticsById(Long id) {
        return engagementStatisticsRepository.findById(id);
    }

    public EngagementStatistics createStatistics(EngagementStatistics statistics) {
        return engagementStatisticsRepository.save(statistics);
    }

    public EngagementStatistics updateStatistics(Long id, EngagementStatistics updatedStatistics) {
        return engagementStatisticsRepository.findById(id)
                .map(statistics -> {
                    statistics.setTotalActivities(updatedStatistics.getTotalActivities());
                    statistics.setMessagesSent(updatedStatistics.getMessagesSent());
                    statistics.setForumPostsMade(updatedStatistics.getForumPostsMade());
                    statistics.setNotificationsReceived(updatedStatistics.getNotificationsReceived());
                    return engagementStatisticsRepository.save(statistics);
                })
                .orElseThrow(() -> new RuntimeException("EngagementStatistics not found"));
    }

    public void deleteStatistics(Long id) {
        engagementStatisticsRepository.deleteById(id);
    }
}
