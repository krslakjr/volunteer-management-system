package com.example.userservice.service;

import com.example.userservice.models.Permission;
import com.example.userservice.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    // Get all permissions
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Get permission by ID
    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    // Create a new permission
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    // Update an existing permission
    public Permission updatePermission(Long id, Permission permission) {
        if (permissionRepository.existsById(id)) {
            return permissionRepository.save(permission);
        }
        return null;  // or throw an exception if not found
    }

    // Delete a permission
    public boolean deletePermission(Long id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
