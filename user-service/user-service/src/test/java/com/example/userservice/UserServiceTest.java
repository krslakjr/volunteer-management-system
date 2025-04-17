package com.example.userservice;

import com.example.userservice.service.UserService;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.models.User;
import com.example.userservice.repository.UserRepository;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setProfilePicture("profilePic.jpg");

        user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setProfilePicture("profilePic.jpg");
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
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser_Found() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<UserDTO> updatedUser = userService.updateUser(1L, userDTO);

        assertTrue(updatedUser.isPresent());
        assertEquals("John", updatedUser.get().getFirstName());
    }

    @Test
    public void testUpdateUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        Optional<UserDTO> updatedUser = userService.updateUser(1L, userDTO);

        assertFalse(updatedUser.isPresent());
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));

        assertEquals("User not found with id 1", exception.getMessage());
    }
}
