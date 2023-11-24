package com.fdmgroup.skillify.dto.skill;

import com.fdmgroup.skillify.enums.ProficiencyLevel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class SkillResponseDto {
    private UUID id;
    @NotEmpty
    private String name;
    @NotNull
    private ProficiencyLevel proficiency;
    private String description;

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

    public SkillResponseDto() {
    }

    public SkillResponseDto(String name, ProficiencyLevel proficiency, String description) {
        this.name = name;
        this.proficiency = proficiency;
        this.description = description;
    }

    @Override
    public String toString() {
        return "SkillResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", proficiency=" + proficiency +
                ", description='" + description + '\'' +
                '}';
    }
}
