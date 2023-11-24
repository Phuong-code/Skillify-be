package com.fdmgroup.skillify.dto.user;

import java.util.List;

import com.fdmgroup.skillify.enums.StatusType;

public class TraineePublicDto {

    private String email;
    private String firstName;
    private String lastName;
    private StatusType type;
	private List<String> skillNames;
	
	public TraineePublicDto() {
		super();

	}

	public TraineePublicDto(String email, String firstName, String lastName, StatusType type, List<String> skillNames) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
		this.skillNames = skillNames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public StatusType getType() {
		return type;
	}

	public void setType(StatusType type) {
		this.type = type;
	}

	public List<String> getSkillNames() {
		return skillNames;
	}

	public void setSkillNames(List<String> skillNames) {
		this.skillNames = skillNames;
	}

	@Override
	public String toString() {
		return "TraineePublicDto [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", type="
				+ type + ", skillNames=" + skillNames + "]";
	}
	
	
    
}
