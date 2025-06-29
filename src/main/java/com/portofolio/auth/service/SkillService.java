package com.portofolio.auth.service;

import com.portofolio.auth.dto.SkillByCategoryResponse;
import com.portofolio.auth.dto.SkillRequest;
import com.portofolio.auth.dto.SkillResponse;

import java.util.List;

public interface SkillService {
    List<SkillResponse> getAll();
    List<SkillResponse> findBySearch(String param) throws Exception;
    SkillResponse getById(Long id);
    SkillResponse create(SkillRequest request) throws Exception;
    SkillResponse update(SkillRequest request);
    void delete(Long id);
    
    List<SkillByCategoryResponse> getAllSkillByCategory();
}