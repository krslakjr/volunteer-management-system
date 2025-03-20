package com.example.feedbackservice.repository;

import com.example.feedbackservice.models.ActivityStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityStatisticsRepository extends JpaRepository<ActivityStatistics, Long> {
}
