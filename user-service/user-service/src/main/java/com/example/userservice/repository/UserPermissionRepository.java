package com.example.userservice.repository;

import com.example.userservice.models.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
    
    // Pronađi permisije korisnika prema userId
    List<UserPermission> findByUser_UserId(Long userId);
}
