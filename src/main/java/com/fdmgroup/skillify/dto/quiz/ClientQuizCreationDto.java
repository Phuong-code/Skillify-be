package com.fdmgroup.skillify.dto.quiz;

import java.util.UUID;

import com.fdmgroup.skillify.enums.QuizType;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class ClientQuizCreationDto {
	@NotEmpty
	private QuizType type;
	@NotEmpty
	private long timeLimit;
	@NotEmpty
	private int totalMark;
	@NotEmpty
	private UUID authorId;
	@NotEmpty
	private UUID placementId;

	
	public ClientQuizCreationDto() {
		super();
	}
	

	public ClientQuizCreationDto(@NotEmpty QuizType type, @NotEmpty long timeLimit, @NotEmpty int totalMark,
			@NotEmpty UUID authorId, @NotEmpty UUID placementId) {
		super();
		this.type = type;
		this.timeLimit = timeLimit;
		this.totalMark = totalMark;
		this.authorId = authorId;
		this.placementId = placementId;
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

	public UUID getPlacementId() {
		return placementId;
	}

	public void setPlacementId(UUID placementId) {
		this.placementId = placementId;
	}

	@Override
	public String toString() {
		return "ClientQuizCreationDto [type=" + type + ", timeLimit=" + timeLimit + ", totalMark=" + totalMark
				+ ", authorId=" + authorId + ", PlacementId=" + placementId + "]";
	}
	
}
