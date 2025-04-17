package com.example.notificationservice.repository;

import com.example.notificationservice.models.ForumPost;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    Page<ForumPost> findAll(Pageable pageable);
}
