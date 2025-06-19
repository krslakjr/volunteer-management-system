package com.example.notificationservice.repository;

import com.example.notificationservice.models.EngagementStatistics;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface EngagementStatisticsRepository extends JpaRepository<EngagementStatistics, Long> {
    Page<EngagementStatistics> findAll(Pageable pageable);
}
