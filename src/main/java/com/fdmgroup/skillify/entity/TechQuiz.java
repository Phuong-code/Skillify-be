package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.QuizType;

import jakarta.persistence.*;


@Entity
@Table(name = "tech_quiz")
public class TechQuiz extends Quiz{
    @OneToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    
	public TechQuiz() {
		super();

	}

	public TechQuiz(QuizType type, long timeLimit, int totalMark, AppUser author) {
		super(type, timeLimit, totalMark, author);

	}	
	public TechQuiz(Skill skill) {
		super();
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "TechQuiz [skill=" + skill + "]";
	}
    
    
}
