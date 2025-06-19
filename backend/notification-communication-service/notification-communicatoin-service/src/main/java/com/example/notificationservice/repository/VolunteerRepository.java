package com.example.notificationservice.repository;

import com.example.notificationservice.models.Volunteer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    List<Volunteer> findByName(String name);

    Page<Volunteer> findAll(Pageable pageable);
}
