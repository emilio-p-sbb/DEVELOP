package com.portofolio.auth.service;

import java.util.List;

import com.portofolio.auth.dto.UserDto;

public interface UserService {
    List<UserDto> getUsers();

    UserDto create(UserDto userDto) throws Exception;

    UserDto getUser(Long userId);

    UserDto updateUser(Long userId, UserDto userDto);

    String deleteUser(Long userId);
}