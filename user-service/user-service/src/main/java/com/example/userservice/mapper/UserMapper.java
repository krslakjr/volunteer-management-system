package com.example.userservice.mapper;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.models.UserPermission;
import com.example.userservice.models.Permission;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setProfilePicture(user.getProfilePicture());

        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toSet()));
        } else {
            dto.setRoles(new HashSet<>());
        }

        if (user.getUserPermissions() != null) {
            dto.setUserPermissions(user.getUserPermissions().stream()
                .map(UserPermission::getPermission)
                .map(Permission::getPermissionName)
                .collect(Collectors.toSet()));
        } else {
            dto.setUserPermissions(new HashSet<>());
        }

        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setProfilePicture(dto.getProfilePicture());

        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());

        return user;
    }
}
