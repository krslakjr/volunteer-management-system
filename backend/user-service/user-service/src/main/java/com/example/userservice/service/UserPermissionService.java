package com.example.userservice.services;

import com.example.userservice.models.UserPermission;
import com.example.userservice.repository.UserPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    public List<UserPermission> getAllUserPermissions() {
        return userPermissionRepository.findAll();
    }

    public List<UserPermission> getAllUserPermissions(Pageable pageable) {
        Page<UserPermission> page = userPermissionRepository.findAll(pageable);
        return page.getContent();
    }

    public List<UserPermission> getUserPermissions(Long userId) {
        return userPermissionRepository.findByUser_UserId(userId);
    }

    public UserPermission addUserPermission(UserPermission userPermission) {
        return userPermissionRepository.save(userPermission);
    }

    @Transactional
    public void deleteUserPermission(Long permissionId) {
        userPermissionRepository.deleteById(permissionId);
    }
}
