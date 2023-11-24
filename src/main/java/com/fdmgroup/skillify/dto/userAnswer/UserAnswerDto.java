package com.fdmgroup.skillify.dto.userAnswer;

import java.util.UUID;

import com.fdmgroup.skillify.dto.question.QuestionDto;

import jakarta.validation.constraints.NotEmpty;

public class UserAnswerDto {
	@NotEmpty
	String traineeEmail;
	@NotEmpty
	UUID questionId;
	@NotEmpty
	String answer;
	QuestionDto questionDto;
	int traineeMark;
	
	
	public UserAnswerDto() {
		super();
	}

	public UserAnswerDto(String traineeEmail, UUID questionId, String answer) {
		super();
		this.traineeEmail = traineeEmail;
		this.questionId = questionId;
		this.answer = answer;
	}

	public QuestionDto getQuestionDto() {
		return questionDto;
	}

	public void setQuestionDto(QuestionDto questionDto) {
		this.questionDto = questionDto;
	}


	public String getTraineeEmail() {
		return traineeEmail;
	}


	public void setTraineeEmail(String traineeEmail) {
		this.traineeEmail = traineeEmail;
	}


	public UUID getQuestionId() {
		return questionId;
	}


	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getTraineeMark() {
		return traineeMark;
	}

	public void setTraineeMark(int traineeMark) {
		this.traineeMark = traineeMark;
	}

}
