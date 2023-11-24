package com.fdmgroup.skillify.dto.quiz;

import com.fdmgroup.skillify.enums.QuizType;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class QuizUpdateDto {
    private UUID id;
    private long timeLimit;
    private int totalMark;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
