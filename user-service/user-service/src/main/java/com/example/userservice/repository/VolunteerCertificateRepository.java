package com.example.userservice.repository;

import com.example.userservice.models.VolunteerCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface VolunteerCertificateRepository extends JpaRepository<VolunteerCertificate, Long> {

    List<VolunteerCertificate> findByUser_UserId(Long userId);

    
    Page<VolunteerCertificate> findAll(Pageable pageable);
}
