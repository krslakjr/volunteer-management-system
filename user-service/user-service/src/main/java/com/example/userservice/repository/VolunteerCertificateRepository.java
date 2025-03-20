package com.example.userservice.repository;

import com.example.userservice.models.VolunteerCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VolunteerCertificateRepository extends JpaRepository<VolunteerCertificate, Long> {

    // Koristi User.userId umesto samo UserId
    List<VolunteerCertificate> findByUser_UserId(Long userId);
}
