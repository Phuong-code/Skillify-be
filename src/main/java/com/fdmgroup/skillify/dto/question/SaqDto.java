package com.fdmgroup.skillify.dto.question;

import java.util.UUID;

import com.fdmgroup.skillify.enums.QuestionType;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class SaqDto extends QuestionDto{

	private String correctAnswer;

	public SaqDto() {
		super();
	}

	public SaqDto(@NotEmpty String question, @NotEmpty int mark, @NotEmpty QuestionType type, @NotEmpty UUID quizId,
			@NotEmpty String correctAnswer) {
		super(question, mark, type, quizId);
		this.correctAnswer = correctAnswer;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public String toString() {
		return "SaqDto{" +
				"question='" + getQuestion() + '\'' +
				", mark=" + getMark() + '\'' +
				", type=" + getType() + '\'' +
				", quizId=" + getQuizId() + '\'' +
				", question_id=" + getQuestion_id() + '\'' +
				", correctAnswer='" + correctAnswer + '\'' +
				'}';
	}
	
}


