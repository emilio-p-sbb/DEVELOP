package com.portofolio.auth.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.auth.dto.UserDto;
import com.portofolio.auth.service.UserService;
import com.portofolio.auth.validation.ValidationService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
    @Autowired UserService userService;
    @Autowired ValidationService validationService;
    
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signup", consumes = {"multipart/form-data"})
    public ResponseEntity<UserDto> createUser(
        @RequestPart("user") UserDto userDto,
        @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) throws Exception {
        UserDto response = userService.create(userDto, avatar);
        return ResponseEntity.created(URI.create("/api/users/" + response.getUserId())).body(response);
    }
    
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> updateUser(
        @RequestPart("user") String userStr,
        @RequestPart(value = "avatar", required = false) MultipartFile avatar
    ) throws JsonProcessingException {
        System.out.println("✅ userStr: " + userStr);
        System.out.println("✅ avatar: " + (avatar != null ? avatar.getOriginalFilename() : "null"));

        ObjectMapper mapper = new ObjectMapper();
        UserDto userDto = mapper.readValue(userStr, UserDto.class);

        System.out.println("userDto json = "+mapper.writeValueAsString(userDto));
        
        UserDto response = userService.updateUser(userDto, avatar);
        return ResponseEntity.ok(response);
    }

    
    
    


//    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<UserDto> updateUser(
//        @RequestPart("user") UserDto userDto,
//        @RequestParam(value = "avatar", required = false) MultipartFile avatar
//    ) {
//    	System.out.println("selesai");
//        UserDto response = userService.updateUser(userDto, avatar);
//        return ResponseEntity.ok(response);
//    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "userId") Long userId) {
        UserDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long userId) {
        String response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }
}