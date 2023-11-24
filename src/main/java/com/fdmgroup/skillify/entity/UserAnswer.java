package com.fdmgroup.skillify.entity;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "user_answer")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private TraineeUser trainee;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "answer", nullable = false)
    private String answer;

	@ManyToOne
	@JoinColumn(name = "quiz_result_id", nullable = false)
	private TraineeQuizResult result;

	@Column(name = "trainee_mark")
	private int traineeMark;

	public TraineeQuizResult getResult() {
		return result;
	}

	public void setResult(TraineeQuizResult result) {
		this.result = result;
	}

	public int getTraineeMark() {
		return traineeMark;
	}

	public void setTraineeMark(int traineeMark) {
		this.traineeMark = traineeMark;
	}

	public UserAnswer() {
		super();
	}

	public UserAnswer(TraineeUser trainee, Question question, String answer) {
		super();
		this.trainee = trainee;
		this.question = question;
		this.answer = answer;

	}

	public UserAnswer(UUID id, TraineeUser trainee, Question question, String answer, TraineeQuizResult result) {
		this.id = id;
		this.trainee = trainee;
		this.question = question;
		this.answer = answer;
		this.result = result;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public TraineeUser getTrainee() {
		return trainee;
	}

	public void setTrainee(TraineeUser trainee) {
		this.trainee = trainee;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "UserAnswer{" +
				"id=" + id +
				", trainee=" + trainee +
				", question=" + question +
				", answer='" + answer + '\'' +
				", result=" + result +
				", traineeMark=" + traineeMark +
				'}';
	}
}
