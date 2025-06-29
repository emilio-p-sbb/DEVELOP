package com.portofolio.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.portofolio.auth.model.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long>, JpaSpecificationExecutor<Experience> {

	List<Experience> findTop3ByOrderByUpdatedAtDesc();
	long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
	
}
