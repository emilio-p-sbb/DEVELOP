package com.portofolio.auth.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto (
		
        Long userId,
        @NotBlank(message = "Full name is required")
        @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
        String fullname,

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
//        @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "Username must contain only letters, numbers, dots, underscores, or hyphens")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
        // Bisa ditambahkan validasi kekuatan password jika perlu
        String password,

        @NotBlank(message = "Gender is required")
        @Pattern(regexp = "Male|Female|Other", message = "Gender must be 'Male', 'Female', or 'Other'")
        String gender,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone must be valid international format (10-15 digits)")
        String phone,

        @Size(max = 255, message = "Avatar URL too long")
        String avatar,

        @NotBlank(message = "Role is required")
        @Pattern(regexp = "USER|ADMIN|MODERATOR", message = "Role must be USER, ADMIN, or MODERATOR")
        String role
//        ,Set<String> premissions
//        List<String> roles
        
)implements Serializable { }
