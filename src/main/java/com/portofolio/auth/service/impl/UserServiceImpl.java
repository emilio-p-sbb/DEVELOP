package com.portofolio.auth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portofolio.auth.dto.UserDto;
import com.portofolio.auth.exception.AlreadyExistsException;
import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.mapper.UserMapper;
import com.portofolio.auth.model.Role;
import com.portofolio.auth.model.User;
import com.portofolio.auth.repository.RoleRepository;
import com.portofolio.auth.repository.UserRepository;
import com.portofolio.auth.service.UserService;
import com.portofolio.auth.validation.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired UserRepository userRepository;
	@Autowired RoleRepository roleRepository;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired ValidationService validationService;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
    }
    
    @Override
    public UserDto create(UserDto userDto, MultipartFile avatar) throws Exception {
        validationService.validate(userDto);
        
        Role role = roleRepository.findByRoleName(userDto.getRole()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
            );
        
        User user = UserMapper.userDtoToUser(userDto,role);

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Data already exists");
        }

//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (avatar != null && !avatar.isEmpty()) {
            user.setAvatar(avatar.getBytes());
        }

        return UserMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, MultipartFile avatar) {
        validationService.validate(userDto);
        
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(
            () -> new ResourceNotFoundException("User not found")
        );

        Role role = roleRepository.findByRoleName(userDto.getRole()).orElseThrow(
            () -> new ResourceNotFoundException("Role not found")
        );

        user.setUsername(userDto.getEmail());
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullname(userDto.getFullname());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());
        user.setLocation(userDto.getLocation());
        user.setGithubUrl(userDto.getGithubUrl());
        user.setLinkedinUrl(userDto.getLinkedinUrl());
        user.setWebsite(userDto.getWebsite());
        user.setTwitterUrl(userDto.getTwitterUrl());
        user.setRole(role);
        user.setBio(userDto.getBio());

        if (avatar != null && !avatar.isEmpty()) {
            try {
                user.setAvatar(avatar.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Failed to read avatar file", e);
            }
        }

        return UserMapper.userToUserDto(userRepository.save(user));
    }


    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return UserMapper.userToUserDto(user);
    }
    @Override
    public String deleteUser(Long userId) {
        // get user from db
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        userRepository.delete(user);

        return String.format("User with %d deleted successfully", userId);
    }

}
