package com.portofolio.auth.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
        String password

) { }

