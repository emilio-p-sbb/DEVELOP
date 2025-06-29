package com.portofolio.auth.service;

import org.springframework.http.ResponseEntity;

import com.portofolio.auth.dto.LoginRequest;
import com.portofolio.auth.dto.LoginResponse;
import com.portofolio.auth.dto.UserLoggedDto;

public interface AuthService {
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken) throws Exception;

    ResponseEntity<LoginResponse> refresh(String refreshToken) throws Exception;

    ResponseEntity<LoginResponse> logout(String accessToken, String refreshToken) throws Exception;

    UserLoggedDto getUserLoggedInfo() throws Exception;
}
