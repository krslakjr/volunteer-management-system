package com.example.userservice.mapper;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : "No Role");
        dto.setPermissions(user.getUserPermissions() != null ? 
            user.getUserPermissions().stream()
                .map(perm -> perm.getPermission().getPermissionName())
                .collect(Collectors.toList()) : null);
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setProfilePicture(dto.getProfilePicture());
        return user;
    }
}
