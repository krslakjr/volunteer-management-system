package com.example.userservice.controller;

import com.example.userservice.models.UserPermission;
import com.example.userservice.services.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userpermissions")
public class UserPermissionController {

    @Autowired
    private UserPermissionService userPermissionService;

    // Dohvati sve UserPermissions
    @GetMapping
    public List<UserPermission> getAllUserPermissions() {
        return userPermissionService.getAllUserPermissions();
    }

    // Dohvati permisije za određenog korisnika
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPermission>> getUserPermissions(@PathVariable Long userId) {
        List<UserPermission> permissions = userPermissionService.getUserPermissions(userId);
        if (permissions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    // Dodaj permisiju korisniku
    @PostMapping
    public ResponseEntity<UserPermission> addUserPermission(@RequestBody UserPermission userPermission) {
        UserPermission createdPermission = userPermissionService.addUserPermission(userPermission);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    // Obriši permisiju
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deleteUserPermission(@PathVariable Long permissionId) {
        userPermissionService.deleteUserPermission(permissionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
