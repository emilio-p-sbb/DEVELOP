package com.portofolio.auth.dto;

import jakarta.validation.constraints.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SkillRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private Long categoryId;

    private String proficiencyLevel;

    @Min(value = 0, message = "Years of experience must be >= 0")
    private Integer yearsOfExperience;

    @Min(value = 0, message = "Percentage must be between 0 and 100")
    @Max(value = 100, message = "Percentage must be between 0 and 100")
    private Integer percentage;

    private String description;
}