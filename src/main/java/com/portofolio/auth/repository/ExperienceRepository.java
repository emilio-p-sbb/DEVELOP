package com.portofolio.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portofolio.auth.model.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
