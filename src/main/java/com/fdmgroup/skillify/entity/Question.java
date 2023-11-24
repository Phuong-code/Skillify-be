package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.QuestionType;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "question", nullable = false)
    private String question;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "mark", nullable = false)
    private int mark;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private QuestionType type;

	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<UserAnswer> userAnswers;

	public Question() {
		super();

	}

	public Question(String question, Quiz quiz, int mark, QuestionType type) {
		super();
		this.question = question;
		this.quiz = quiz;
		this.mark = mark;
		this.type = type;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", question=" + question + ", quiz=" + quiz + ", mark=" + mark + ", type=" + type
				+ "]";
	}
    
}
