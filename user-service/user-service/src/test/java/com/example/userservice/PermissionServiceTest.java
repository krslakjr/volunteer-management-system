package com.example.userservice;

import com.example.userservice.controller.PermissionController;
import com.example.userservice.models.Permission;
import com.example.userservice.repository.PermissionRepository;
import com.example.userservice.service.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionService permissionService;

    private Permission permission;

    @BeforeEach
    public void setUp() {
        permission = new Permission();
        permission.setPermissionId(1L);
        permission.setPermissionName("VIEW_DASHBOARD");
    }

    @Test
    public void testGetAllPermissions() {
        when(permissionRepository.findAll()).thenReturn(Arrays.asList(permission));

        assertNotNull(permissionService.getAllPermissions());
        assertEquals(1, permissionService.getAllPermissions().size());
    }

    @Test
    public void testGetPermissionById_Found() {
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        Optional<Permission> result = permissionService.getPermissionById(1L);
        assertTrue(result.isPresent());
        assertEquals("VIEW_DASHBOARD", result.get().getPermissionName());
    }

    @Test
    public void testGetPermissionById_NotFound() {
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Permission> result = permissionService.getPermissionById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreatePermission() {
        when(permissionRepository.save(permission)).thenReturn(permission);

        Permission createdPermission = permissionService.createPermission(permission);
        assertNotNull(createdPermission);
        assertEquals("VIEW_DASHBOARD", createdPermission.getPermissionName());
        verify(permissionRepository, times(1)).save(permission);
    }

    @Test
    public void testUpdatePermission_Found() {

        when(permissionRepository.existsById(1L)).thenReturn(true);
        when(permissionRepository.save(permission)).thenReturn(permission);

        Permission updatedPermission = permissionService.updatePermission(1L, permission);
        assertNotNull(updatedPermission);
        assertEquals("VIEW_DASHBOARD", updatedPermission.getPermissionName());
        verify(permissionRepository, times(1)).save(permission);
    }

    @Test
    public void testUpdatePermission_NotFound() {

        when(permissionRepository.existsById(1L)).thenReturn(false);

        Permission updatedPermission = permissionService.updatePermission(1L, permission);
        assertNull(updatedPermission);
    }

    @Test
    public void testDeletePermission_Found() {
        when(permissionRepository.existsById(1L)).thenReturn(true);

        boolean result = permissionService.deletePermission(1L);
        assertTrue(result);
        verify(permissionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePermission_NotFound() {
        when(permissionRepository.existsById(1L)).thenReturn(false);

        boolean result = permissionService.deletePermission(1L);
        assertFalse(result);
    }
}
