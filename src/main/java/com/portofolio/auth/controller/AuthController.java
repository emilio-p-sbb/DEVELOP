package com.portofolio.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portofolio.auth.dto.LoginRequest;
import com.portofolio.auth.dto.LoginResponse;
import com.portofolio.auth.dto.UserLoggedDto;
import com.portofolio.auth.service.AuthService;
import com.portofolio.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
    @Autowired AuthService authService;
    @Autowired UserService userService;
    
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> login(
            @CookieValue(name = "access_token", required = false) String accessToken,
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest)  throws Exception{
    	System.out.println("masuk loginkah?");
        return authService.login(loginRequest, accessToken, refreshToken);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@CookieValue(name = "refresh_token", required = true) String refreshToken)  throws Exception{
        return authService.refresh(refreshToken);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(
            @CookieValue(name = "access_token", required = false) String accessToken,
            @CookieValue(name = "refresh_token", required = false) String refreshToken)  throws Exception{
        return authService.logout(accessToken, refreshToken);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public ResponseEntity<UserLoggedDto> userLoggedInfo() throws Exception{
        return ResponseEntity.ok(authService.getUserLoggedInfo());
    }
}