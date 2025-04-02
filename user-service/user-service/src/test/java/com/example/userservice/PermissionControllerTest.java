package com.example.userservice;

import com.example.userservice.controller.PermissionController;
import com.example.userservice.models.Permission;
import com.example.userservice.service.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.userservice.exception.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PermissionControllerTest {

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private PermissionController permissionController;

    private MockMvc mockMvc;

    private Permission permission;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(permissionController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();

        permission = new Permission();
        permission.setPermissionId(1L);
        permission.setPermissionName("VIEW_DASHBOARD");
    }

    @Test
    public void testGetAllPermissions() throws Exception {
        when(permissionService.getAllPermissions()).thenReturn(Arrays.asList(permission));

        mockMvc.perform(get("/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].permissionId").value(1))
                .andExpect(jsonPath("$[0].permissionName").value("VIEW_DASHBOARD"));
    }

    @Test
    public void testGetPermissionById_Found() throws Exception {
        when(permissionService.getPermissionById(1L)).thenReturn(Optional.of(permission));

        mockMvc.perform(get("/permissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissionId").value(1))
                .andExpect(jsonPath("$.permissionName").value("VIEW_DASHBOARD"));
    }

    @Test
    public void testGetPermissionById_NotFound() throws Exception {
        
        when(permissionService.getPermissionById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/permissions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePermission() throws Exception {
        when(permissionService.createPermission(any(Permission.class))).thenReturn(permission);

        mockMvc.perform(post("/permissions")
                        .contentType("application/json")
                        .content("{\"permissionName\": \"VIEW_DASHBOARD\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.permissionId").value(1))
                .andExpect(jsonPath("$.permissionName").value("VIEW_DASHBOARD"));
    }

    @Test
    public void testCreatePermission_InternalServerError() throws Exception {

        when(permissionService.createPermission(any(Permission.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/permissions")
                        .contentType("application/json")
                        .content("{\"permissionName\": \"VIEW_DASHBOARD\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
public void testUpdatePermission_Found() throws Exception {
    permission.setPermissionName("EDIT_DASHBOARD");

    when(permissionService.updatePermission(eq(1L), any(Permission.class))).thenReturn(permission);

    mockMvc.perform(put("/permissions/1")
                        .contentType("application/json")
                        .content("{\"permissionName\": \"EDIT_DASHBOARD\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissionId").value(1))
                .andExpect(jsonPath("$.permissionName").value("EDIT_DASHBOARD"));
}


    @Test
    public void testUpdatePermission_NotFound() throws Exception {
       
        when(permissionService.updatePermission(eq(1L), any(Permission.class))).thenReturn(null);

        mockMvc.perform(put("/permissions/1")
                        .contentType("application/json")
                        .content("{\"permissionName\": \"EDIT_DASHBOARD\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePermission_Found() throws Exception {
        when(permissionService.deletePermission(1L)).thenReturn(true);

        mockMvc.perform(delete("/permissions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePermission_NotFound() throws Exception {
        when(permissionService.deletePermission(1L)).thenReturn(false);

        mockMvc.perform(delete("/permissions/1"))
                .andExpect(status().isNotFound());
    }
}
