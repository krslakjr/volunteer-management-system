package com.example.notificationservice.service;

import com.example.notificationservice.models.EngagementStatistics;
import com.example.notificationservice.repository.EngagementStatisticsRepository;
import com.example.notificationservice.exception.ResourceNotFoundException;
import com.example.notificationservice.models.Organizer;


import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    
    public List<EngagementStatistics> getAllStatistics(Pageable pageable) {
        Page<EngagementStatistics> page = engagementStatisticsRepository.findAll(pageable);
        return page.getContent();
    }

    public Optional<EngagementStatistics> getStatisticsById(Long id) {
        return engagementStatisticsRepository.findById(id);
    }

   
    @Transactional
    public EngagementStatistics createStatistics(EngagementStatistics statistics) {
        try {
            return engagementStatisticsRepository.save(statistics);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating engagement statistics", e);  
        }
    }

    @Transactional
    public EngagementStatistics updateStatistics(Long id, EngagementStatistics updatedStatistics) {
        return engagementStatisticsRepository.findById(id)
                .map(statistics -> {
                    statistics.setTotalActivities(updatedStatistics.getTotalActivities());
                    statistics.setMessagesSent(updatedStatistics.getMessagesSent());
                    statistics.setForumPostsMade(updatedStatistics.getForumPostsMade());
                    statistics.setNotificationsReceived(updatedStatistics.getNotificationsReceived());
                    return engagementStatisticsRepository.save(statistics); 
                })
                .orElseThrow(() -> new ResourceNotFoundException("Engagement Statistics not found with id " + id, "id"));
    }


@Transactional
    public void deleteStatistics(Long id) {
        if (engagementStatisticsRepository.existsById(id)) {
            engagementStatisticsRepository.deleteById(id);  
        } else {
            throw new ResourceNotFoundException("Engagement Statistics not found with id " + id, "id");
        }
    }
    }
