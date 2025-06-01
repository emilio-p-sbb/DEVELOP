package com.portofolio.auth.model;



import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "skill")
public class Skill extends BaseEntity {

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "skill_seq", sequenceName = "skill_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_seq")
	@Column(name = "skill_id", nullable = false)
    private Long skillId;

    @Column(nullable = false)
    private String name;

    private Long categoryId;

    @Column(name = "proficiency_level")
    private String proficiencyLevel;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    private Integer percentage;

    private String description;
    
}
