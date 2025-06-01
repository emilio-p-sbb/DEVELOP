package com.portofolio.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "experience")
public class Experience extends BaseEntity{

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "experience_seq", sequenceName = "experience_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "experience_seq")
	@Column(name = "experience_id", nullable = false)
    private Long experienceId;

    @Column(name = "company")
    private String company;

    @Column(name = "position")
    private String position;

    @Column(name = "location")
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "responsibilities", columnDefinition = "TEXT")
    private String resposibilities;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    private Boolean isCurrent;

}