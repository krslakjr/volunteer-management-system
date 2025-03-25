package com.example.feedbackservice.service;

import com.example.feedbackservice.models.ActivityStatistics;
import com.example.feedbackservice.repository.ActivityStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityStatisticsService {

    @Autowired
    private ActivityStatisticsRepository activityStatisticsRepository;
    
    public List<ActivityStatistics> getAllActivityStatistics() {
        return activityStatisticsRepository.findAll();
    }

    public Optional<ActivityStatistics> getActivityStatisticsById(Long id) {
        return activityStatisticsRepository.findById(id);
    }

    public ActivityStatistics saveOrUpdateActivityStatistics(ActivityStatistics activityStatistics) {
        return activityStatisticsRepository.save(activityStatistics);
    }

    public void deleteActivityStatistics(Long id) {
        if (activityStatisticsRepository.existsById(id)) {
            activityStatisticsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Activity Statistics not found with id " + id);
        }
    }
}
