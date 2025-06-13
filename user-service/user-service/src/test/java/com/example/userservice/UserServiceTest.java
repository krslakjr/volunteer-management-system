package com.example.userservice;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private User user;

    @BeforeEach
    public void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("johndoe");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("test123");
        registerRequest.setProfilePicture("profilePic.jpg");

        user = new User();
        user.setUserId(1L);
        user.setUsername("johndoe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPasswordHash("hashedpassword");
        user.setProfilePicture("profilePic.jpg");
        user.setRoles(new HashSet<>());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
    }

    @Test
    public void testCreateUser() {
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashedpassword");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(new Role(1L, "USER")));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createdUser = userService.createUser(registerRequest);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("johndoe", createdUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<UserDTO> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getFirstName());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<UserDTO> foundUser = userService.getUserById(1L);
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        boolean result = userService.deleteUser(1L);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        boolean result = userService.deleteUser(1L);
        assertFalse(result);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
