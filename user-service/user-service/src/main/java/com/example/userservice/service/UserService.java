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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper) { // Dodan UserMapper u konstruktor
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper; // Inicijalizacija
    }

    @Transactional
    public UserDTO registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Korisničko ime je već zauzeto!");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email je već zauzet!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setProfilePicture(registerRequest.getProfilePicture());


        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role defaultRole = roleRepository.findByRoleName("ROLE_VOLUNTEER")
                    .orElseThrow(() -> new RuntimeException("Greška: Uloga 'VOLUNTEER' nije pronađena."));
            roles.add(defaultRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_ADMIN is not found."));
                        roles.add(adminRole);
                        break;
                    case "organizer":
                        Role organizerRole = roleRepository.findByRoleName("ROLE_ORGANIZER")
                                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_ORGANIZER is not found."));
                        roles.add(organizerRole);
                        break;
                    default:
                        Role volunteerRole = roleRepository.findByRoleName("ROLE_VOLUNTEER")
                                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_VOLUNTEER is not found."));
                        roles.add(volunteerRole);
                }
            });
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long userId) {
        return userRepository.findById(userId).map(userMapper::toDTO);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            userDTO.getRoles().forEach(roleName -> {
                roleRepository.findByRoleName(roleName)
                        .ifPresent(roles::add);
            });
            user.setRoles(roles);
        } else {
            roleRepository.findByRoleName("ROLE_VOLUNTEER")
                    .ifPresent(role -> user.setRoles(Set.of(role)));
        }

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            existingUser.setFirstName(userDTO.getFirstName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setProfilePicture(userDTO.getProfilePicture());
            existingUser.setUsername(userDTO.getUsername());

            if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
                Set<Role> roles = new HashSet<>();
                for (String roleName : userDTO.getRoles()) {
                    roleRepository.findByRoleName(roleName)
                            .ifPresentOrElse(
                                    roles::add,
                                    () -> {
                                        throw new RuntimeException("Error: Role '" + roleName + "' is not found.");
                                    }
                            );
                }
                existingUser.setRoles(roles);
            } else {
                existingUser.setRoles(new HashSet<>());
            }

            User updatedUser = userRepository.save(existingUser);
            return userMapper.toDTO(updatedUser);
        }
        return null;
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
