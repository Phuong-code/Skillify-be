package com.fdmgroup.skillify.dto.question;

import java.util.List;
import java.util.UUID;

import com.fdmgroup.skillify.entity.Option;
import com.fdmgroup.skillify.enums.QuestionType;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class McqDto extends QuestionDto{

	@NotEmpty
	private List<Option> options;

	public McqDto() {
		super();
	}

	public McqDto(@NotEmpty String question, @NotEmpty int mark, @NotEmpty QuestionType type, @NotEmpty UUID quizId,
			@NotEmpty List<Option> options) {
		super(question, mark, type, quizId);
		this.options = options;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "McqDto{" +
				"question='" + getQuestion() + '\'' +
				", mark=" + getMark() + '\'' +
				", type=" + getType() + '\'' +
				", quizId=" + getQuizId() + '\'' +
				", question_id=" + getQuestion_id() + '\'' +
				", options='" + options + '\'' +
				'}';
	}
}
