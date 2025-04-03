package com.example.userservice;

import com.example.userservice.controller.RoleController;
import com.example.userservice.models.Role;
import com.example.userservice.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.userservice.exception.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");
    }




    @Test
    public void testGetRoleById_Found() {
        when(roleService.getRoleById(1L)).thenReturn(Optional.of(role));

        ResponseEntity<Role> response = roleController.getRoleById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ADMIN", response.getBody().getRoleName());
    }

    @Test
public void testGetRoleById_NotFound() {
    doThrow(new ResourceNotFoundException("Role not found with id 1", "id"))
        .when(roleService).getRoleById(1L);

    assertThrows(ResourceNotFoundException.class, () -> {
        roleController.getRoleById(1L);
    });
}


    @Test
    public void testCreateRole() {
        when(roleService.createRole(role)).thenReturn(role);

        ResponseEntity<Role> response = roleController.createRole(role);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ADMIN", response.getBody().getRoleName());
    }

    @Test
    public void testUpdateRole_Found() {
        when(roleService.updateRole(1L, role)).thenReturn(role);

        ResponseEntity<Role> response = roleController.updateRole(1L, role);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ADMIN", response.getBody().getRoleName());
    }

    @Test
    public void testUpdateRole_NotFound() {
        when(roleService.updateRole(1L, role)).thenReturn(null);

        ResponseEntity<Role> response = roleController.updateRole(1L, role);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteRole_Found() {
        when(roleService.deleteRole(1L)).thenReturn(true);

        ResponseEntity<HttpStatus> response = roleController.deleteRole(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteRole_NotFound() {
        when(roleService.deleteRole(1L)).thenReturn(false);

        ResponseEntity<HttpStatus> response = roleController.deleteRole(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
