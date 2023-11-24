package com.fdmgroup.skillify.dto.quiz;

import com.fdmgroup.skillify.dto.user.UserPublicDto;
import com.fdmgroup.skillify.enums.QuizType;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public abstract class QuizDto {
    private UUID id;
    @NotEmpty
    private QuizType type;
    @NotEmpty
    private long timeLimit;
    @NotEmpty
    private int totalMark;
    @NotEmpty
    private UserPublicDto author;
    
    public QuizDto() {
        super();
    }

    public QuizDto(UUID id, @NotEmpty QuizType type, @NotEmpty long timeLimit, @NotEmpty int totalMark,
            @NotEmpty UserPublicDto author) {
        super();
        this.id = id;
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

    public UserPublicDto getAuthor() {
        return author;
    }

    public void setAuthor(UserPublicDto author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "QuizDto{" +
                "id=" + id +
                ", type=" + type +
                ", timeLimit=" + timeLimit +
                ", totalMark=" + totalMark +
                ", author=" + author +
                '}';
    }
}
