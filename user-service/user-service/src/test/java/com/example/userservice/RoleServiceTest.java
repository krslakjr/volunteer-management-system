package com.example.userservice;

import com.example.userservice.models.Role;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");
    }

    @Test
    public void testGetAllRoles() {
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role));

        List<Role> roles = roleService.getAllRoles();
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0).getRoleName());
    }

    @Test
    public void testGetRoleById_Found() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Optional<Role> foundRole = roleService.getRoleById(1L);
        assertTrue(foundRole.isPresent());
        assertEquals("ADMIN", foundRole.get().getRoleName());
    }

    @Test
    public void testGetRoleById_NotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Role> foundRole = roleService.getRoleById(1L);
        assertFalse(foundRole.isPresent());
    }

    @Test
    public void testCreateRole() {
        when(roleRepository.save(role)).thenReturn(role);

        Role createdRole = roleService.createRole(role);
        assertNotNull(createdRole);
        assertEquals("ADMIN", createdRole.getRoleName());
    }

    @Test
    public void testUpdateRole_Found() {
        when(roleRepository.existsById(1L)).thenReturn(true);
        when(roleRepository.save(role)).thenReturn(role);

        Role updatedRole = roleService.updateRole(1L, role);
        assertNotNull(updatedRole);
        assertEquals("ADMIN", updatedRole.getRoleName());
    }

    @Test
    public void testUpdateRole_NotFound() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        Role updatedRole = roleService.updateRole(1L, role);
        assertNull(updatedRole);
    }

    @Test
    public void testDeleteRole_Found() {
        when(roleRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = roleService.deleteRole(1L);
        assertTrue(isDeleted);
        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteRole_NotFound() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = roleService.deleteRole(1L);
        assertFalse(isDeleted);
        verify(roleRepository, times(0)).deleteById(1L);
    }
}
