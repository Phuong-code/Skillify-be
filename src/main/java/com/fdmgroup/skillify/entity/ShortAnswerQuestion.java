package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.QuestionType;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "short_answer_question")
public class ShortAnswerQuestion extends Question{
    @Column(name = "correct_answer")
    private String correctAnswer;

	public ShortAnswerQuestion() {
		super();

	}

	public ShortAnswerQuestion(String question, Quiz quiz, int mark, QuestionType type) {
		super(question, quiz, mark, type);

	}

	public ShortAnswerQuestion(String correctAnswer) {
		super();
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
		return "ShortAnswerQuestion [correctAnswer=" + correctAnswer + "]";
	}
    
    
}
