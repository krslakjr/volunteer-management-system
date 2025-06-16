package com.example.userservice.service;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Kreiranje korisnika (registracija)
    public UserDTO createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setProfilePicture(registerRequest.getProfilePicture());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        // Dodaj default ulogu "USER"
        Optional<Role> defaultRole = roleRepository.findByRoleName("USER");
        Set<Role> roles = new HashSet<>();
        defaultRole.ifPresent(roles::add);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    // Dohvati korisnika po ID
    public Optional<UserDTO> getUserById(Long userId) {
        return userRepository.findById(userId).map(UserMapper::toDTO);
    }

    // Dohvati sve korisnike
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User u : users) {
            dtos.add(UserMapper.toDTO(u));
        }
        return dtos;
    }

    // Update korisnika po ID
    public Optional<UserDTO> updateUser(Long userId, UserDTO userDTO) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setUpdatedAt(new Date());

        // Update uloga ako su prisutne u DTO
        if (userDTO.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : userDTO.getRoles()) {
                roleRepository.findByRoleName(roleName).ifPresent(roles::add);
            }
            user.setRoles(roles);
        }

        // Ako želiš dodati ažuriranje permisija, možeš to ovdje proširiti

        User savedUser = userRepository.save(user);
        return Optional.of(UserMapper.toDTO(savedUser));
    }

    // Brisanje korisnika po ID
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<String> getAllAdminEmails() {
        return userRepository.findAllAdminEmails();
    }
}
