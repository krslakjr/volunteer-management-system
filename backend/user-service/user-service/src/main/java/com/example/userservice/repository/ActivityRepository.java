package com.example.userservice.repository;

import com.example.userservice.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    
}
 
