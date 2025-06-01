package com.portofolio.auth.mapper;


import java.util.stream.Collectors;

import com.portofolio.auth.dto.UserDto;
import com.portofolio.auth.dto.UserLoggedDto;
import com.portofolio.auth.model.Permission;
import com.portofolio.auth.model.User;

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
                user.getAvatar(),
                user.getRole().getAuthority()
//                ,user.getRole().getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toSet())
        );
    }
    public static User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setFullname(dto.fullname());
        user.setEmail(dto.email());
        user.setGender(dto.gender());
        user.setPhone(dto.phone());
        user.setAvatar(dto.avatar());
        user.setUsername(dto.username());
        return user;
    }
    public static UserLoggedDto userToUserLoggedDto(User user) {
        return new UserLoggedDto(
                user.getUsername(),
                user.getRole().getAuthority(),
                user.getRole().getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toSet())
        );
    }
}
