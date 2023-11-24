package com.fdmgroup.skillify.dto.quiz;

import com.fdmgroup.skillify.enums.ProficiencyLevel;
import com.fdmgroup.skillify.enums.QuizType;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class TechQuizPublicDto {
    @NotEmpty
    private UUID id;
    @NotEmpty
    private QuizType type;
    @NotEmpty
    private long timeLimit;
    @NotEmpty
    private int totalMark;
    @NotEmpty
    private UUID skillId;
    @NotEmpty
    private UUID authorId;
    @NotEmpty
    private String skillName;
    @NotEmpty
    private ProficiencyLevel proficiency;
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public QuizType getType() {
        return type;
    }

    public void setType(QuizType type) {
        this.type = type;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public UUID getSkillId() {
        return skillId;
    }

    public void setSkillId(UUID skillId) {
        this.skillId = skillId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    
    public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public ProficiencyLevel getProficiency() {
		return proficiency;
	}

	public void setProficiency(ProficiencyLevel proficiency) {
		this.proficiency = proficiency;
	}

	@Override
    public String toString() {
        return "TechQuizPublicDto{" +
                "id=" + id +
                ", type=" + type +
                ", timeLimit=" + timeLimit +
                ", totalMark=" + totalMark +
                ", skillId=" + skillId +
                ", authorId=" + authorId +
                '}';
    }
}
