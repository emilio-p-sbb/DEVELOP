package com.portofolio.auth.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portofolio.auth.dto.UserDto;
import com.portofolio.auth.service.UserService;
import com.portofolio.auth.validation.ValidationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
    @Autowired UserService userService;
    @Autowired ValidationService validationService;
    
    @CrossOrigin(origins = "*")
    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception{
    	System.out.println("kesini?");
        UserDto response = userService.create(userDto);
        return ResponseEntity.created(URI.create("/api/users/" + response.userId())).body(response);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "userId") Long userId) {
        UserDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "userId") Long userId,@RequestBody UserDto userDto) {
    	validationService.validate(userDto);
        UserDto response = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long userId) {
        String response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }
}