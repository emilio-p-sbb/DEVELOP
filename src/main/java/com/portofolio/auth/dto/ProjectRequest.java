package com.portofolio.auth.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectRequest {
	private Long projectId;
    private String title;
    private String description;
    private String technologies;
    private String imageUrl;
    private String demoUrl;
    private String codeUrl;
    private boolean featured;
}
