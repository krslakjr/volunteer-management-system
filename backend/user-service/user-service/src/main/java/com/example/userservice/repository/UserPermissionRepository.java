package com.example.userservice.repository;

import com.example.userservice.models.UserPermission;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
    
    List<UserPermission> findByUser_UserId(Long userId);

    
    Page<UserPermission> findAll(Pageable pageable);
}
