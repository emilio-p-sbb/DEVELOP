package com.portofolio.auth.mapper;

import com.portofolio.auth.dto.UserDto;
import com.portofolio.auth.dto.UserLoggedDto;
import com.portofolio.auth.model.Permission;
import com.portofolio.auth.model.Role;
import com.portofolio.auth.model.User;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto userToUserDto(User user) {
    	
    	String base64Avatar = null;
        if (user.getAvatar() != null && user.getAvatar().length > 0) {
            base64Avatar = Base64.getEncoder().encodeToString(user.getAvatar());
        }
        
        return new UserDto(
                user.getUserId(),
                user.getFullname(),
//                user.getUsername(),
                user.getEmail(),
//                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                base64Avatar,
//                null, // Avatar MultipartFile tidak bisa direpresentasikan dalam DTO Response
//                user.getRole().getAuthority(),
                user.getRole().getRoleName(),
                user.getLocation(),
                user.getWebsite(),
                user.getGithubUrl(),
                user.getLinkedinUrl(),
                user.getTwitterUrl(),
                user.getBio()
        );
    }

    public static User userDtoToUser(UserDto dto, Role role) {
        return User.builder()
                .userId(dto.getUserId())
                .fullname(dto.getFullname())
                .email(dto.getEmail())
//                .password(dto.getPassword()) // pastikan sudah di-encode sebelum save
                .gender(dto.getGender())
                .phone(dto.getPhone())
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
