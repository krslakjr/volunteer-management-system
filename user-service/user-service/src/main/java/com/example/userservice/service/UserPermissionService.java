package com.example.userservice.services;

import com.example.userservice.models.UserPermission;
import com.example.userservice.repository.UserPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    public List<UserPermission> getAllUserPermissions() {
        return userPermissionRepository.findAll();
    }

    public List<UserPermission> getUserPermissions(Long userId) {
        return userPermissionRepository.findByUser_UserId(userId);
    }

    public UserPermission addUserPermission(UserPermission userPermission) {
        return userPermissionRepository.save(userPermission);
    }

    public void deleteUserPermission(Long permissionId) {
        userPermissionRepository.deleteById(permissionId);
    }
}
