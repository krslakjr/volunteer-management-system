package com.example.notificationservice.repository;

import com.example.notificationservice.models.Message;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAll(Pageable pageable);
}
