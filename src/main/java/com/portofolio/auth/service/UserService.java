package com.portofolio.auth.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.portofolio.auth.dto.UserDto;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUser(Long userId);
    UserDto create(UserDto userDto, MultipartFile avatar) throws Exception;
    UserDto updateUser(UserDto userDto, MultipartFile avatar);
    String deleteUser(Long userId);
}