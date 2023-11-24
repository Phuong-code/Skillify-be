package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.ProficiencyLevel;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "proficiency", nullable = false)
    private ProficiencyLevel proficiency;

    @Column
    private String description;

	public Skill() {
		super();

	}

	public Skill(String name, ProficiencyLevel proficiency, String description) {
		super();
		this.name = name;
		this.proficiency = proficiency;
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProficiencyLevel getProficiency() {
		return proficiency;
	}

	public void setProficiency(ProficiencyLevel proficiency) {
		this.proficiency = proficiency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Skill [id=" + id + ", name=" + name + ", proficiency=" + proficiency + ", description=" + description
				+ "]";
	}
    
    
}
