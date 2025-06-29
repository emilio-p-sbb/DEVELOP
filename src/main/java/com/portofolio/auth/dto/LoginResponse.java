package com.portofolio.auth.dto;

public record LoginResponse(
        boolean isLogged,
        String role,
        String accessToken,
        String refreshToken,
        UserInformation information
) {
}