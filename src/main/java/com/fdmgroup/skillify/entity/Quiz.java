package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.QuizType;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "quiz")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private QuizType type;

    @Column(name = "time_limit")
    private long timeLimit;

    @Column(name = "total_mark", nullable = false)
    private int totalMark;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AppUser author;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Question> questions;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TraineeQuizResult> results;

	public Quiz() {
		super();
	}

	public Quiz(QuizType type, long timeLimit, int totalMark, AppUser author) {
		super();
		this.type = type;
		this.timeLimit = timeLimit;
		this.totalMark = totalMark;
		this.author = author;
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

	public AppUser getAuthor() {
		return author;
	}

	public void setAuthor(AppUser author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", type=" + type + ", timeLimit=" + timeLimit + ", totalMark=" + totalMark
				+ ", author=" + author + "]";
	}
    
}
