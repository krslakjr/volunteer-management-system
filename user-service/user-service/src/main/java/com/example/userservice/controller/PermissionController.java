package com.example.userservice.controller;

import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.models.Permission;
import com.example.userservice.service.PermissionService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<Permission> getAllPermissions(@RequestParam(required = false) Integer page, 
                                            @RequestParam(required = false) Integer size) {
        Pageable pageable = Pageable.unpaged();
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size);
        }
        return permissionService.getAllPermissions(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Optional<Permission> permission = permissionService.getPermissionById(id);
        return permission.map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + id, "id"));
}

    @PostMapping
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission) {
        try {
            Permission createdPermission = permissionService.createPermission(permission);
            return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id,@Valid @RequestBody Permission permission) {
        Permission updatedPermission = permissionService.updatePermission(id, permission);
        if (updatedPermission != null) {
            return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePermission(@PathVariable Long id) {
        if (permissionService.deletePermission(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}