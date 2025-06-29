package com.portofolio.auth.mapper;

import com.portofolio.auth.dto.UserDto;
import com.portofolio.auth.dto.UserLoggedDto;
import com.portofolio.auth.model.Permission;
import com.portofolio.auth.model.Role;
import com.portofolio.auth.model.User;

import java.io.IOException;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto userToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                null, // Avatar MultipartFile tidak bisa direpresentasikan dalam DTO Response
                user.getRole().getAuthority(),
                user.getLocation(),
                user.getWebsite(),
                user.getGithubUrl(),
                user.getLinkedinUrl(),
                user.getTwitterUrl(),
                user.getBio()
        );
    }

    public static User userDtoToUser(UserDto dto, Role role) {
        byte[] avatarBytes = null;
        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            try {
                avatarBytes = dto.getAvatar().getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Failed to read avatar file", e);
            }
        }

        return User.builder()
                .userId(dto.getUserId())
                .fullname(dto.getFullname())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword()) // pastikan sudah di-encode sebelum save
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .avatar(avatarBytes)
                .role(role)
                .location(dto.getLocation())
                .website(dto.getWebsite())
                .githubUrl(dto.getGithubUrl())
                .linkedinUrl(dto.getLinkedinUrl())
                .twitterUrl(dto.getTwitterUrl())
                .bio(dto.getBio())
                .build();
    }


    public static UserLoggedDto userToUserLoggedDto(User user) {
        return new UserLoggedDto(
                user.getUsername(),
                user.getRole().getAuthority(),
                user.getRole().getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toSet())
        );
    }
}
