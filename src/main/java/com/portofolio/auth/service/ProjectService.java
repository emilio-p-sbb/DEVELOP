package com.portofolio.auth.service;

import com.portofolio.auth.dto.ProjectRequest;
import com.portofolio.auth.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getAll();
    List<ProjectResponse> findBySearch(String keyword) throws Exception;
    ProjectResponse getById(Long id);
    ProjectResponse create(ProjectRequest request) throws Exception;
    ProjectResponse update(ProjectRequest request);
    void delete(Long id);
}
