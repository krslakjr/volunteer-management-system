package com.example.participationservice.repository;

import com.example.participationservice.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Page<Participation> findAll(Pageable pageable);
}
