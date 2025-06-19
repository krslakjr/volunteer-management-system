package com.example.userservice.repository;

import com.example.userservice.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    Page<Permission> findAll(Pageable pageable);
}
