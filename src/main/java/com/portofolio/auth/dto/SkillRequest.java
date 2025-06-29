package com.portofolio.auth.dto;

import jakarta.validation.constraints.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SkillRequest {
	
	private Long skillId;

    @NotBlank(message = "Name is required")
    private String name;

    private String category;

    @Min(value = 0, message = "Proficiency Level must be between 0 and 100")
    @Max(value = 100, message = "Proficiency Level must be between 0 and 100")
    private Integer proficiencyLevel;

    @Min(value = 0, message = "Years of experience must be >= 0")
    private Integer yearsOfExperience;

    private String description;
}