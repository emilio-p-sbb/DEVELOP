package com.portofolio.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.portofolio.auth.dto.ProjectRequest;
import com.portofolio.auth.dto.ProjectResponse;
import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.model.Project;
import com.portofolio.auth.repository.ProjectRepository;
import com.portofolio.auth.service.ProjectService;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<ProjectResponse> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
        return toResponse(project);
    }

    @Override
    public ProjectResponse create(ProjectRequest request) throws Exception {
        Project project = toEntity(request);
        return toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse update(ProjectRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + request.getProjectId()));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setImageUrl(request.getImageUrl());
        project.setDemoUrl(request.getDemoUrl());
        project.setCodeUrl(request.getCodeUrl());
        project.setFeatured(request.isFeatured());

        return toResponse(projectRepository.save(project));
    }

    @Override
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id " + id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectResponse> findBySearch(String keyword) throws Exception {
        Specification<Project> spec = (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return cb.conjunction();
            }

            String pattern = "%" + keyword.toLowerCase() + "%";

            Predicate titlePredicate = cb.like(cb.lower(root.get("title")), pattern);
            Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), pattern);
            Predicate techPredicate = cb.like(cb.lower(root.get("technologies")), pattern);

            return cb.or(titlePredicate, descriptionPredicate, techPredicate);
        };

        return projectRepository.findAll(spec)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private Project toEntity(ProjectRequest request) {
        return Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .technologies(request.getTechnologies())
                .imageUrl(request.getImageUrl())
                .demoUrl(request.getDemoUrl())
                .codeUrl(request.getCodeUrl())
                .featured(request.isFeatured())
                .build();
    }

    private ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .imageUrl(project.getImageUrl())
                .demoUrl(project.getDemoUrl())
                .codeUrl(project.getCodeUrl())
                .featured(project.isFeatured())
                .createdAt(project.getCreatedAt())
                .build();
    }
}
