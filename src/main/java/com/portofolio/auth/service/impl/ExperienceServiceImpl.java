package com.portofolio.auth.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
        return experienceRepository.findAll().stream()
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
    public ExperienceResponse update(Long id, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id " + id));

        experience.setCompany(request.getCompany());
        experience.setPosition(request.getPosition());
        experience.setLocation(request.getLocation());
        experience.setDescription(request.getDescription());
        experience.setResposibilities(request.getResposibilities());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrent(request.getIsCurrent());
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
                .id(experience.getExperienceId())
                .company(experience.getCompany())
                .position(experience.getPosition())
                .location(experience.getLocation())
                .description(experience.getDescription())
                .resposibilities(experience.getResposibilities())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .isCurrent(experience.getIsCurrent())
                .createdAt(experience.getCreatedAt())
                .updatedAt(experience.getUpdatedAt())
                .build();
    }

    private Experience toEntity(ExperienceRequest request) {
        return Experience.builder()
                .company(request.getCompany())
                .position(request.getPosition())
                .location(request.getLocation())
                .description(request.getDescription())
                .resposibilities(request.getResposibilities())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isCurrent(request.getIsCurrent())
                .build();
    }
}