package com.example.userservice;

import com.example.userservice.services.UserPermissionService;
import com.example.userservice.models.UserPermission;
import com.example.userservice.models.User;
import com.example.userservice.models.Permission;
import com.example.userservice.repository.UserPermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserPermissionServiceTest {

    @Mock
    private UserPermissionRepository userPermissionRepository;

    @InjectMocks
    private UserPermissionService userPermissionService;

    private UserPermission userPermission;

    @BeforeEach
    public void setUp() {
        userPermission = new UserPermission();
        userPermission.setId(1L);
        userPermission.setUser(new User());
        userPermission.setPermission(new Permission()); 
    }


    @Test
    public void testGetUserPermissions_Found() {
        List<UserPermission> userPermissions = Arrays.asList(userPermission);

        when(userPermissionRepository.findByUser_UserId(1L)).thenReturn(userPermissions);

        List<UserPermission> result = userPermissionService.getUserPermissions(1L);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testGetUserPermissions_NotFound() {
        when(userPermissionRepository.findByUser_UserId(1L)).thenReturn(Collections.emptyList());

        List<UserPermission> result = userPermissionService.getUserPermissions(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testAddUserPermission() {
        when(userPermissionRepository.save(userPermission)).thenReturn(userPermission);

        UserPermission result = userPermissionService.addUserPermission(userPermission);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testDeleteUserPermission() {
        doNothing().when(userPermissionRepository).deleteById(1L);

        userPermissionService.deleteUserPermission(1L);
        verify(userPermissionRepository, times(1)).deleteById(1L);
    }
}
