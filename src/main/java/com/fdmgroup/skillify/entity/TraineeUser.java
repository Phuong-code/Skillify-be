package com.fdmgroup.skillify.entity;

import com.fdmgroup.skillify.enums.RoleType;

import com.fdmgroup.skillify.enums.StatusType;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "trainee")
public class TraineeUser extends AppUser{
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @ManyToMany
    @JoinTable(
        name = "trainee_skill",
        joinColumns = @JoinColumn(name = "trainee_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

	public TraineeUser() {
		super();

	}

	public TraineeUser(String email, String password, String firstName, String lastName, boolean authenticated,
			RoleType role) {
		super(email, password, firstName, lastName, authenticated, role);

	}

	public TraineeUser(StatusType status, Set<Skill> skills) {
		super();
		this.status = status;
		this.skills = skills;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	
	public void addSkill(Skill skill) {
		skills.add(skill);
	}

	@Override
	public String toString() {
		return "TraineeUser [status=" + status + ", skills=" + skills + "]";
	}
    
    
}
