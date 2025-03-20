package com.example.userservice.repository;

import com.example.userservice.models.SocialShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialShareRepository extends JpaRepository<SocialShare, Long> {
}
