package com.fdmgroup.skillify.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "trainee_quiz_result")
public class TraineeQuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private TraineeUser trainee;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "score")
    private int score;

    @Column(name = "finished_marking")
    private boolean finishedMarking;

    @Column(name = "submission_date")
    @CreationTimestamp
    private Timestamp submissionDate;

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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
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

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public String toString() {
        return "TraineeQuizResult{" +
                "id=" + id +
                ", trainee=" + trainee +
                ", quiz=" + quiz +
                ", score=" + score +
                ", finishedMarking=" + finishedMarking +
                ", submissionDate=" + submissionDate +
                '}';
    }
}
