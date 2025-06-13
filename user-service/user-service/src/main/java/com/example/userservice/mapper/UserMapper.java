package com.example.userservice.mapper;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getEmail()); // Ili neko drugo polje za username ako imaš
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setProfilePicture(user.getProfilePicture());

        // Mapiraj role iz Set<Role> u Set<String> roleName
        if (user.getRoles() != null) {
            dto.setRoles(
                user.getRoles()
                    .stream()
                    .map(role -> role.getRoleName())
                    .collect(Collectors.toSet())
            );
        }

        if (user.getUserPermissions() != null) {
            dto.setUserPermissions(
                user.getUserPermissions()
                    .stream()
                    .map(perm -> perm.getPermission().getPermissionName()) // ide preko Permission objekta
                    .collect(Collectors.toSet())
            );
        }

        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        // Nemamo username u User modelu, pa možda email koristimo kao username
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setProfilePicture(dto.getProfilePicture());
        // Roles i permissions mapiranje možeš napraviti u servisu ili dodati ovdje ako želiš
        return user;
    }
}
