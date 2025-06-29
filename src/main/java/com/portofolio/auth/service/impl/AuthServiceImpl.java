package com.portofolio.auth.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.auth.dto.LoginRequest;
import com.portofolio.auth.dto.LoginResponse;
import com.portofolio.auth.dto.UserInformation;
import com.portofolio.auth.dto.UserLoggedDto;
import com.portofolio.auth.exception.AppException;
import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.jwt.JwtTokenProvider;
import com.portofolio.auth.mapper.UserMapper;
import com.portofolio.auth.model.Token;
import com.portofolio.auth.model.User;
import com.portofolio.auth.repository.TokenRepository;
import com.portofolio.auth.repository.UserRepository;
import com.portofolio.auth.service.AuthService;
import com.portofolio.auth.util.CookieUtil;
import com.portofolio.auth.validation.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
    @Value("${jwt.access-token.duration.minute}")
    private long accessTokenDurationMinute;
    
    @Value("${jwt.access-token.duration.second}")
    private long accessTokenDurationSecond;
    
    @Value("${jwt.refresh-token.duration.day}")
    private long refreshTokenDurationDay;
    
    @Value("${jwt.refresh-token.duration.second}")
    private long refreshTokenDurationSecond;
    
    @Autowired UserRepository userRepository;
    @Autowired TokenRepository tokenRepository;
    @Autowired JwtTokenProvider tokenProvider;
    @Autowired CookieUtil cookieUtil;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired ValidationService validationService;
    
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken)  throws Exception{
    	
    	System.out.println("login = "+new ObjectMapper().writeValueAsString(loginRequest));
    	
    	validationService.validate(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password()
                )
        );

        String username = loginRequest.username();

        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        System.out.println("user = "+user);

        boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken = null, newRefreshToken = null;

        revokeAllTokenOfUser(user);

        if(!accessTokenValid && !refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(
                    Map.of("role", user.getRole().getAuthority()),
                    accessTokenDurationMinute,
                    ChronoUnit.MINUTES,
                    user
            );

            newRefreshToken = tokenProvider.generateRefreshToken(
                    refreshTokenDurationDay,
                    ChronoUnit.DAYS,
                    user
            );

            newAccessToken.setUser(user);
            newRefreshToken.setUser(user);

            // save tokens in db
            tokenRepository.saveAll(List.of(newAccessToken, newRefreshToken));

            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        if(!accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(
                    Map.of("role", user.getRole().getAuthority()),
                    accessTokenDurationMinute,
                    ChronoUnit.MINUTES,
                    user
            );

            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        if(accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(
                    Map.of("role", user.getRole().getAuthority()),
                    accessTokenDurationMinute,
                    ChronoUnit.MINUTES,
                    user
            );

            newRefreshToken = tokenProvider.generateRefreshToken(
                    refreshTokenDurationDay,
                    ChronoUnit.DAYS,
                    user
            );

            newAccessToken.setUser(user);
            newRefreshToken.setUser(user);

            // save tokens in db
            tokenRepository.saveAll(List.of(newAccessToken, newRefreshToken));

            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserInformation information = UserInformation.builder()
        		.userId(user.getUserId())
        		.email(user.getEmail())
        		.fullname(user.getFullname())
        		.phone(user.getPhone())
        		.build();
        LoginResponse loginResponse = new LoginResponse(true, user.getRole().getRoleName(), newAccessToken.getValue(),newRefreshToken.getValue(),information);

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }
    @Override
    public ResponseEntity<LoginResponse> refresh(String refreshToken)  throws Exception{
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        if(!refreshTokenValid)
            throw new AppException(HttpStatus.BAD_REQUEST, "Refresh token is invalid");

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        Token newAccessToken = tokenProvider.generateAccessToken(
                Map.of("role", user.getRole().getAuthority()),
                accessTokenDurationMinute,
                ChronoUnit.MINUTES,
                user
        );

//        HttpHeaders responseHeaders = new HttpHeaders();
//        addAccessTokenCookie(responseHeaders, newAccessToken);

        UserInformation information = UserInformation.builder()
        		.userId(user.getUserId())
        		.email(user.getEmail())
        		.fullname(user.getFullname())
        		.phone(user.getPhone())
        		.build();
        LoginResponse loginResponse = new LoginResponse(true, user.getRole().getRoleName(),newAccessToken.getValue(),newAccessToken.getValue(),information);

        return ResponseEntity.ok().body(loginResponse);
//        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }
    @Override
    public ResponseEntity<LoginResponse> logout(String accessToken, String refreshToken)  throws Exception{
        SecurityContextHolder.clearContext();

        String username = tokenProvider.getUsernameFromToken(accessToken);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        revokeAllTokenOfUser(user);

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteAccessTokenCookie().toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteRefreshTokenCookie().toString());

        LoginResponse loginResponse = new LoginResponse(false, null,null,null,null);

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }
    @Override
    public UserLoggedDto getUserLoggedInfo() throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken)
            throw new AppException(HttpStatus.UNAUTHORIZED, "No user authenticated");

        String username = authentication.getName();

        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        return UserMapper.userToUserLoggedDto(user);
    }
    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.getValue(), accessTokenDurationSecond).toString());
    }
    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(token.getValue(), refreshTokenDurationSecond).toString());
    }
    private void revokeAllTokenOfUser(User user) {
        // get all user tokens
        Set<Token> tokens = user.getTokens();

        tokens.forEach(token -> {
            if(token.getExpiryDate().isBefore(LocalDateTime.now()))
                tokenRepository.delete(token);
            else if(!token.isDisabled()) {
                token.setDisabled(true);
                tokenRepository.save(token);
            }
        });
    }
}