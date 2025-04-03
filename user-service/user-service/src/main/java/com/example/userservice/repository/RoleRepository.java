package com.example.userservice.repository;

import com.example.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    List<Role> findByRoleName(String roleName);
    Page<Role> findAll(Pageable pageable);
}
