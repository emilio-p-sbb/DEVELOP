package com.portofolio.auth.service.impl;

import com.portofolio.auth.dto.EducationRequest;
import com.portofolio.auth.dto.EducationResponse;
import com.portofolio.auth.model.Education;
import com.portofolio.auth.repository.EducationRepository;
import com.portofolio.auth.service.EducationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Override
    public List<EducationResponse> getAll() {
        return educationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EducationResponse getById(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with id: " + id));
        return toResponse(education);
    }

    @Override
    public EducationResponse create(EducationRequest request) {
        Education education = toEntity(request);
        education.setEducationId(null); // ensure ID is null for new record
        return toResponse(educationRepository.save(education));
    }

    @Override
    public EducationResponse update(EducationRequest request) {
        Education existing = educationRepository.findById(request.getEducationId())
                .orElseThrow(() -> new EntityNotFoundException("Education not found with id: " + request.getEducationId()));

        existing.setDegree(request.getDegree());
        existing.setInstitution(request.getInstitution());
        existing.setLocation(request.getLocation());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setDescription(request.getDescription());
        existing.setGpa(request.getGpa());
        existing.setCurrent(request.isCurrent());

        return toResponse(educationRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!educationRepository.existsById(id)) {
            throw new EntityNotFoundException("Education not found with id: " + id);
        }
        educationRepository.deleteById(id);
    }

    private EducationResponse toResponse(Education education) {
        return EducationResponse.builder()
                .educationId(education.getEducationId())
                .degree(education.getDegree())
                .institution(education.getInstitution())
                .location(education.getLocation())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .description(education.getDescription())
                .gpa(education.getGpa())
                .current(education.isCurrent())
                .build();
    }

    private Education toEntity(EducationRequest request) {
        return Education.builder()
                .educationId(request.getEducationId())
                .degree(request.getDegree())
                .institution(request.getInstitution())
                .location(request.getLocation())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .gpa(request.getGpa())
                .current(request.isCurrent())
                .build();
    }
}
