package com.example.activitymanagement.repository;

import com.example.activitymanagement.models.TeamActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamActivityRepository extends JpaRepository<TeamActivity, Long> {
}