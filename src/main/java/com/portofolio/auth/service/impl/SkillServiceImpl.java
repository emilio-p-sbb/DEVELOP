package com.portofolio.auth.service.impl;

import com.portofolio.auth.dto.SkillByCategoryResponse;
import com.portofolio.auth.dto.SkillRequest;
import com.portofolio.auth.dto.SkillResponse;
import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.model.Skill;
import com.portofolio.auth.repository.SkillRepository;
import com.portofolio.auth.service.SkillService;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    @Autowired SkillRepository skillRepository;

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
    public SkillResponse create(SkillRequest request)  throws Exception{
        Skill skill = toEntity(request);
        return toResponse(skillRepository.save(skill));
    }

    @Override
    public SkillResponse update(SkillRequest request) {
        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id " + request.getSkillId()));

        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        skill.setProficiencyLevel(request.getProficiencyLevel());
        skill.setYearsOfExperience(request.getYearsOfExperience());
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
                .category(request.getCategory())
                .proficiencyLevel(request.getProficiencyLevel())
                .yearsOfExperience(request.getYearsOfExperience())
                .description(request.getDescription())
                .build();
    }

    private SkillResponse toResponse(Skill skill) {
        return SkillResponse.builder()
                .skillId(skill.getSkillId())
                .name(skill.getName())
                .category(skill.getCategory())
                .proficiencyLevel(skill.getProficiencyLevel())
                .yearsOfExperience(skill.getYearsOfExperience())
                .description(skill.getDescription())
                .build();
    }

	@Override
	public List<SkillResponse> findBySearch(String keyword) throws Exception {
		Specification<Skill> spec = (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return cb.conjunction();
            }

            String pattern = "%" + keyword.toLowerCase() + "%";

            Predicate namePredicate = cb.like(cb.lower(root.get("name")), pattern);
            Predicate categoryPredicate = cb.like(cb.lower(root.get("category")), pattern);
            Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), pattern);
            Predicate proficiencyPredicate = cb.like(cb.concat("", root.get("proficiencyLevel").as(String.class)), pattern);
            Predicate experiencePredicate = cb.like(cb.concat("", root.get("yearsOfExperience").as(String.class)), pattern);

            return cb.or(namePredicate, categoryPredicate, descriptionPredicate, proficiencyPredicate, experiencePredicate);
        };

        return skillRepository.findAll(spec).stream()
                .map(skill -> SkillResponse.builder()
                        .skillId(skill.getSkillId())
                        .name(skill.getName())
                        .category(skill.getCategory())
                        .proficiencyLevel(skill.getProficiencyLevel())
                        .yearsOfExperience(skill.getYearsOfExperience())
                        .description(skill.getDescription())
                        .build()
                ).toList();
	}

	@Override
	public List<SkillByCategoryResponse> getAllSkillByCategory() {
		List<Skill> allSkills = skillRepository.findAll();

        // Grouping by category
        Map<String, List<Skill>> grouped = allSkills.stream()
            .collect(Collectors.groupingBy(skill -> {
                String cat = skill.getCategory();
                return cat != null ? cat : "Uncategorized";
            }));

     // Transforming to DTO
        return grouped.entrySet().stream()
            .map(entry -> SkillByCategoryResponse.builder()
                .category(entry.getKey())
                .skills(
                    entry.getValue().stream()
                        .map(skill -> SkillByCategoryResponse.SkillItem.builder() // gunakan qualified class
                            .name(skill.getName())
                            .level(skill.getProficiencyLevel())
                            .build())
                        .collect(Collectors.toList())
                )
                .build())
            .collect(Collectors.toList());
	}
}