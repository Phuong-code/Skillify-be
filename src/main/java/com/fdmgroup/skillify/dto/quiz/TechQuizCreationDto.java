package com.fdmgroup.skillify.dto.quiz;

import java.util.UUID;

import com.fdmgroup.skillify.enums.ProficiencyLevel;
import com.fdmgroup.skillify.enums.QuizType;

import jakarta.validation.constraints.NotEmpty;

public class TechQuizCreationDto {
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
	private String skillName;
	@NotEmpty
	private ProficiencyLevel skillProficiency;
	@NotEmpty
	private UUID authorId;

	public TechQuizCreationDto() {
		super();
	}

	public TechQuizCreationDto(UUID id, @NotEmpty QuizType type, @NotEmpty long timeLimit, @NotEmpty int totalMark,
			@NotEmpty UUID skillId, @NotEmpty String skillName, @NotEmpty ProficiencyLevel skillProficiency,  @NotEmpty UUID authorId) {
		super();
		this.id = id;
		this.type = type;
		this.timeLimit = timeLimit;
		this.totalMark = totalMark;
		this.skillId = skillId;
		this.skillName = skillName;
		this.skillProficiency = skillProficiency;
		this.authorId = authorId;
	}
	
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

	public UUID getAuthorId() {
		return authorId;
	}

	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}

	public UUID getSkillId() {
		return skillId;
	}

	public void setSkillId(UUID skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	
	public ProficiencyLevel getSkillProficiency() {
		return skillProficiency;
	}

	public void setSkillProficiency(ProficiencyLevel skillProficiency) {
		this.skillProficiency = skillProficiency;
	}

	@Override
	public String toString() {
		return "TechQuizCreationDto [id=" + id + ", type=" + type + ", timeLimit=" + timeLimit + ", totalMark="
				+ totalMark + ", skillId=" + skillId + ", skillName=" + skillName + ", skillProficiency="
				+ skillProficiency + ", authorId=" + authorId + "]";
	}

	
}
