package com.portofolio.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.portofolio.auth.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {

	List<Skill> findTop3ByOrderByUpdatedAtDesc();
	long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
