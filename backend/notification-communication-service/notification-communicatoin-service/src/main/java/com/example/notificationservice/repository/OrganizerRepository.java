package com.example.notificationservice.repository;

import com.example.notificationservice.models.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    
    List<Organizer> findByName(String name);

    Page<Organizer> findAll(Pageable pageable);
}
