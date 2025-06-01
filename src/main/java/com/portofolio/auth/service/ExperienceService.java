package com.portofolio.auth.service;


import java.util.List;

import com.portofolio.auth.dto.ExperienceRequest;
import com.portofolio.auth.dto.ExperienceResponse;

public interface ExperienceService {

    List<ExperienceResponse> getAll();
    ExperienceResponse getById(Long id);
    ExperienceResponse create(ExperienceRequest request);
    ExperienceResponse update(Long id, ExperienceRequest request);
    void delete(Long id);
}