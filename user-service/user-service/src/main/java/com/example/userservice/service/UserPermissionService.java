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

    // Dohvati sve permissions
    public List<UserPermission> getAllUserPermissions() {
        return userPermissionRepository.findAll();
    }

    // Dohvati sve permisije za određenog korisnika
    public List<UserPermission> getUserPermissions(Long userId) {
        return userPermissionRepository.findByUser_UserId(userId);
    }

    // Dodaj permisiju korisniku
    public UserPermission addUserPermission(UserPermission userPermission) {
        return userPermissionRepository.save(userPermission);
    }

    // Obriši permisiju
    public void deleteUserPermission(Long permissionId) {
        userPermissionRepository.deleteById(permissionId);
    }
}
