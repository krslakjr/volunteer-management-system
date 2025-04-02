package com.example.userservice.repository;

import com.example.userservice.models.UserPermission;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
    
    List<UserPermission> findByUser_UserId(Long userId);
}
