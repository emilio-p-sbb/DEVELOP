package com.portofolio.auth.repository;

import com.portofolio.auth.model.Project;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
	
	List<Project> findTop3ByOrderByUpdatedAtDesc();
	long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
