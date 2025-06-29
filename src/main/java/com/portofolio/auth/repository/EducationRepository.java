package com.portofolio.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portofolio.auth.model.Education;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    
    List<Education> findAllByOrderByStartDateDesc();
    
    List<Education> findByCurrentTrueOrderByStartDateDesc();
    
    List<Education> findByInstitutionContainingIgnoreCase(String institution);
    
    List<Education> findByDegreeContainingIgnoreCase(String degree);
    
    List<Education> findTop3ByOrderByUpdatedAtDesc();
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}