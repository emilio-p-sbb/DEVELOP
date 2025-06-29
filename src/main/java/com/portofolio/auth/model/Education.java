package com.portofolio.auth.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "education")
public class Education extends BaseEntity{

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "education_seq", sequenceName = "education_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_seq")
	@Column(name = "education_id", nullable = false)
    private Long educationId;
	
	@Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    private String institution;

    private String location;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String gpa;

    @Column(name = "is_current")
    private boolean current = false;
	
}
