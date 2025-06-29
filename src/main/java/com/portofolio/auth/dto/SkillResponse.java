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
    private String category;
    private Integer proficiencyLevel;
    private Integer yearsOfExperience;
    private String description;
    
}