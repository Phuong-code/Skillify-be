package com.fdmgroup.skillify.dto.quiz;

import com.fdmgroup.skillify.dto.skill.SkillResponseDto;
import jakarta.validation.constraints.NotEmpty;

public class TechQuizResponseDto extends QuizDto{
    @NotEmpty
    private SkillResponseDto skill;

    public TechQuizResponseDto() {
        super();
    }

    public SkillResponseDto getSkill() {
        return skill;
    }

    public void setSkill(SkillResponseDto skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "TechQuizResponseDto{" +
                "skill=" + skill +
                '}';
    }
}
