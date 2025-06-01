package com.portofolio.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SkillResponse {

    private Long skillId;
    private String name;
    private Long categoryId;
    private String proficiencyLevel;
    private Integer yearsOfExperience;
    private Integer percentage;
    private String description;
    
}