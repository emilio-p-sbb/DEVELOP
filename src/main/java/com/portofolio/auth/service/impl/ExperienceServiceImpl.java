package com.portofolio.auth.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.portofolio.auth.dto.ExperienceRequest;
import com.portofolio.auth.dto.ExperienceResponse;
import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.model.Experience;
import com.portofolio.auth.repository.ExperienceRepository;
import com.portofolio.auth.service.ExperienceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired ExperienceRepository experienceRepository;

    @Override
    public List<ExperienceResponse> getAll() {
        return experienceRepository.findAll(Sort.by(Sort.Direction.DESC, "endDate")).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExperienceResponse getById(Long id) {
        Experience exp = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id " + id));
        return toResponse(exp);
    }

    @Override
    public ExperienceResponse create(ExperienceRequest request) {
        Experience experience = toEntity(request);
        experience.setCreatedAt(LocalDateTime.now());
        experience.setUpdatedAt(LocalDateTime.now());
        return toResponse(experienceRepository.save(experience));
    }

    @Override
    public ExperienceResponse update(ExperienceRequest request) {
        Experience experience = experienceRepository.findById(request.getExperienceId())
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id " + request.getExperienceId()));

        experience.setCompanyName(request.getCompany());
        experience.setPositionTitle(request.getPosition());
        experience.setLocation(request.getLocation());
        experience.setDescription(request.getDescription());
        experience.setResponsibilities(request.getResponsibilities());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentPosition(request.getIsCurrent());
        experience.setUpdatedAt(LocalDateTime.now());

        return toResponse(experienceRepository.save(experience));
    }

    @Override
    public void delete(Long id) {
        if (!experienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Experience not found with id " + id);
        }
        experienceRepository.deleteById(id);
    }

    private ExperienceResponse toResponse(Experience experience) {
        return ExperienceResponse.builder()
                .experienceId(experience.getExperienceId())
                .company(experience.getCompanyName())
                .position(experience.getPositionTitle())
                .location(experience.getLocation())
                .description(experience.getDescription())
                .responsibilities(experience.getResponsibilities())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .isCurrent(experience.getCurrentPosition())
                .createdAt(experience.getCreatedAt())
                .updatedAt(experience.getUpdatedAt())
                .build();
    }

    private Experience toEntity(ExperienceRequest request) {
        return Experience.builder()
        		.experienceId(request.getExperienceId())
                .companyName(request.getCompany())
                .positionTitle(request.getPosition())
                .location(request.getLocation())
                .description(request.getDescription())
                .responsibilities(request.getResponsibilities())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .currentPosition(request.getIsCurrent())
                .build();
    }
}