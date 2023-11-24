package com.fdmgroup.skillify.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "multiple_choice_question")
public class MultipleChoiceQuestion extends Question{
    @Column(name = "options", nullable = false, columnDefinition = "json")
	@Convert(converter = OptionsConverter.class)
    private List<Option> options;

	public MultipleChoiceQuestion() {
		super();
	}
    
	public MultipleChoiceQuestion(List<Option> options) {
		super();
		this.options = options;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "MultipleChoiceQuestion{" +
				"options=" + options +
				'}';
	}

}
