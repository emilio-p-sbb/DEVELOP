package com.portofolio.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100)
    private String fullname;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64)
    private String password;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other")
    private String gender;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phone;

    private MultipartFile avatar; // tetap di sini jika kamu kirim avatar di bagian JSON

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "USER|ADMIN|MODERATOR")
    private String role;

    @Size(max = 128)
    private String location;

    @Size(max = 255)
    private String website;

    @Size(max = 255)
    private String githubUrl;

    @Size(max = 255)
    private String linkedinUrl;

    @Size(max = 255)
    private String twitterUrl;

    @Size(max = 500)
    private String bio;
}
