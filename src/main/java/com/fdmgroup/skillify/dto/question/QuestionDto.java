package com.fdmgroup.skillify.dto.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fdmgroup.skillify.enums.QuestionType;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = McqDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = SaqDto.class, name = "SHORT_ANSWER")
})
public abstract class QuestionDto {

    private UUID question_id;

    @NotEmpty
    private String question;

    @NotEmpty
    private int mark;

    @NotEmpty
    private QuestionType type;

    @NotEmpty
    private UUID quizId;

    public QuestionDto() {
        super();
    }

    public QuestionDto(String question, int mark, QuestionType type, UUID quizId) {
        this.question = question;
        this.mark = mark;
        this.type = type;
        this.quizId = quizId;
    }

    public UUID getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(UUID question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public UUID getQuizId() {
        return quizId;
    }

    public void setQuizId(UUID quizId) {
        this.quizId = quizId;
    }

}
