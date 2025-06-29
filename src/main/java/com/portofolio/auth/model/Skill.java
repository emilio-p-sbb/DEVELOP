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

    @Column(nullable = false, length = 128)
    private String name;

    @Column(name = "category", length = 128)
    private String category;

    @Column(name = "proficiency_level")
    private Integer proficiencyLevel;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    private String description;
    
}
