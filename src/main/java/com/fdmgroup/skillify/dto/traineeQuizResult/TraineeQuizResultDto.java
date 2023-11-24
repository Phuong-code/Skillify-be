package com.fdmgroup.skillify.dto.traineeQuizResult;

import com.fdmgroup.skillify.dto.quiz.QuizDto;
import com.fdmgroup.skillify.dto.user.TraineePublicDto;
import com.fdmgroup.skillify.dto.user.UserPublicDto;

import java.util.UUID;

public class TraineeQuizResultDto {

	private UUID id;
	private UserPublicDto trainee;
	private QuizDto quiz;
	private int score;
	private boolean finishedMarking;
	private String submissionDate;
	
	public TraineeQuizResultDto() {
		super();

	}

	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public QuizDto getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizDto quiz) {
		this.quiz = quiz;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isFinishedMarking() {
		return finishedMarking;
	}

	public void setFinishedMarking(boolean finishedMarking) {
		this.finishedMarking = finishedMarking;
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}

	public UserPublicDto getTrainee() {
		return trainee;
	}

	public void setTrainee(UserPublicDto trainee) {
		this.trainee = trainee;
	}


	@Override
	public String toString() {
		return "TraineeQuizResultDto [id=" + id + ", trainee=" + trainee + ", quiz=" + quiz + "]";
	}
	
	
}
