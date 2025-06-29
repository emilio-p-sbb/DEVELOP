package com.portofolio.auth.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "project")
public class Project extends BaseEntity {

    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "technology")
    private String technologies;
    
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "demo_url")
    private String demoUrl;

    @Column(name = "code_url")
    private String codeUrl;

    @Column(name = "featured")
    private boolean featured;

}
