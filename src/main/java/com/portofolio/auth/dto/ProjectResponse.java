package com.portofolio.auth.dto;

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
public class ProjectResponse {
    private Long projectId;
    private String title;
    private String description;
    private String technologies;
    private String imageUrl;
    private String demoUrl;
    private String codeUrl;
    private boolean featured;
    private LocalDateTime createdAt;
}
