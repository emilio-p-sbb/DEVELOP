package com.portofolio.auth.service;

import java.util.List;

import com.portofolio.auth.dto.EducationRequest;
import com.portofolio.auth.dto.EducationResponse;

public interface EducationService {

	List<EducationResponse> getAll();
	EducationResponse getById(Long id);
	EducationResponse create(EducationRequest request);
    EducationResponse update(EducationRequest request);
    void delete(Long id);
    
}
