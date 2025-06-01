package com.portofolio.auth.service;

import com.portofolio.auth.dto.SkillRequest;
import com.portofolio.auth.dto.SkillResponse;

import java.util.List;

public interface SkillService {
    List<SkillResponse> getAll();
    SkillResponse getById(Long id);
    SkillResponse create(SkillRequest request);
    SkillResponse update(Long id, SkillRequest request);
    void delete(Long id);
}