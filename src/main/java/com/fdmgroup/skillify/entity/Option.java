package com.fdmgroup.skillify.entity;

/**
 * This class is used to represent an option in a multiple choice question.
 */
public class Option {
    private String title;
    private String content;
    private boolean correct;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Option(String title, String content, boolean correct) {
        this.title = title;
        this.content = content;
        this.correct = correct;
    }

    public Option() {
    }

    @Override
    public String toString() {
        return "Option{" +
                "option='" + title + '\'' +
                ", answer='" + content + '\'' +
                ", isCorrect=" + correct +
                '}';
    }
}
