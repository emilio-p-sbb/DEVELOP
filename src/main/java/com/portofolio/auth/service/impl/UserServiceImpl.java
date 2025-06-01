package com.portofolio.auth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDto create(UserDto userDto) throws Exception{
    	validationService.validate(userDto);
        User user = UserMapper.userDtoToUser(userDto);

        // get role from db
        Role role = roleRepository.findByRoleName(userDto.role()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        );
        
        Optional<User> userExist = userRepository.findByUsername(userDto.email());
        if(userExist.isPresent()) {
        	throw new AlreadyExistsException("Data already exists");
        }

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userDto.password()));

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
    public UserDto updateUser(Long userId, UserDto userDto) {
        // get user from db
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        // get role from db
        Role role = roleRepository.findByRoleName(userDto.role()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        );

        user.setUsername(userDto.username());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(role);

        return UserMapper.userToUserDto(userRepository.save(user));
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
