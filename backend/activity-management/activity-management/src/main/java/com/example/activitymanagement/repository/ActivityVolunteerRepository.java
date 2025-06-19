package com.example.activitymanagement.repository;

import com.example.activitymanagement.models.ActivityVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityVolunteerRepository extends JpaRepository<ActivityVolunteer, Long> {
}