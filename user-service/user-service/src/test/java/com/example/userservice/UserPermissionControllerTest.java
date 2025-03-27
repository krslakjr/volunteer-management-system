package com.example.userservice;

import com.example.userservice.models.User;
import com.example.userservice.models.Permission;
import com.example.userservice.controller.UserPermissionController;
import com.example.userservice.models.UserPermission;
import com.example.userservice.services.UserPermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserPermissionControllerTest {

    @Mock
    private UserPermissionService userPermissionService;

    @InjectMocks
    private UserPermissionController userPermissionController;

    private UserPermission userPermission;

    @BeforeEach
    public void setUp() {
        userPermission = new UserPermission();
        userPermission.setId(1L);
        userPermission.setUser(new User()); 
        userPermission.setPermission(new Permission());
    }

    @Test
    public void testGetAllUserPermissions() {
        List<UserPermission> userPermissions = Arrays.asList(userPermission);

        when(userPermissionService.getAllUserPermissions()).thenReturn(userPermissions);

        List<UserPermission> response = userPermissionController.getAllUserPermissions();
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
    }

    @Test
    public void testGetUserPermissions_Found() {
        List<UserPermission> userPermissions = Arrays.asList(userPermission);

        when(userPermissionService.getUserPermissions(1L)).thenReturn(userPermissions);

        ResponseEntity<List<UserPermission>> response = userPermissionController.getUserPermissions(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
    }

    @Test
    public void testGetUserPermissions_NotFound() {
        when(userPermissionService.getUserPermissions(1L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserPermission>> response = userPermissionController.getUserPermissions(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testAddUserPermission() {
        when(userPermissionService.addUserPermission(userPermission)).thenReturn(userPermission);

        ResponseEntity<UserPermission> response = userPermissionController.addUserPermission(userPermission);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testDeleteUserPermission() {
        doNothing().when(userPermissionService).deleteUserPermission(1L);

        ResponseEntity<Void> response = userPermissionController.deleteUserPermission(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(userPermissionService, times(1)).deleteUserPermission(1L);
    }
}
