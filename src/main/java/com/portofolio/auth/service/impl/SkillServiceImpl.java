package com.portofolio.auth.service.impl;

import com.portofolio.auth.dto.SkillRequest;
import com.portofolio.auth.dto.SkillResponse;
import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.model.Skill;
import com.portofolio.auth.repository.SkillRepository;
import com.portofolio.auth.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public List<SkillResponse> getAll() {
        return skillRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SkillResponse getById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id " + id));
        return toResponse(skill);
    }

    @Override
    public SkillResponse create(SkillRequest request) {
        Skill skill = toEntity(request);
        return toResponse(skillRepository.save(skill));
    }

    @Override
    public SkillResponse update(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id " + id));

        skill.setName(request.getName());
        skill.setCategoryId(request.getCategoryId());
        skill.setProficiencyLevel(request.getProficiencyLevel());
        skill.setYearsOfExperience(request.getYearsOfExperience());
        skill.setPercentage(request.getPercentage());
        skill.setDescription(request.getDescription());

        return toResponse(skillRepository.save(skill));
    }

    @Override
    public void delete(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id " + id);
        }
        skillRepository.deleteById(id);
    }

    private Skill toEntity(SkillRequest request) {
        return Skill.builder()
                .name(request.getName())
                .categoryId(request.getCategoryId())
                .proficiencyLevel(request.getProficiencyLevel())
                .yearsOfExperience(request.getYearsOfExperience())
                .percentage(request.getPercentage())
                .description(request.getDescription())
                .build();
    }

    private SkillResponse toResponse(Skill skill) {
        return SkillResponse.builder()
                .skillId(skill.getSkillId())
                .name(skill.getName())
                .categoryId(skill.getCategoryId())
                .proficiencyLevel(skill.getProficiencyLevel())
                .yearsOfExperience(skill.getYearsOfExperience())
                .percentage(skill.getPercentage())
                .description(skill.getDescription())
                .build();
    }
}