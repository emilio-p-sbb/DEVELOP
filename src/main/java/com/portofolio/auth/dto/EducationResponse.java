package com.portofolio.auth.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EducationResponse {

	private Long educationId;
    private String degree;
    private String institution;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String gpa;
    private boolean current;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
