package com.fdmgroup.skillify.dto.user;

import com.fdmgroup.skillify.enums.RoleType;

import lombok.Data;

import java.util.List;
import java.util.UUID;

public class UserPublicDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private RoleType role;
    
	private List<String> skillNames;

	public UserPublicDto() {
		super();
	}
	
	public UserPublicDto(String email, String firstName, String lastName, RoleType role,
			List<String> skillNames) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.skillNames = skillNames;
	}



	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public List<String> getSkillNames() {
		return skillNames;
	}

	public void setSkillNames(List<String> skillNames) {
		this.skillNames = skillNames;
	}

	@Override
	public String toString() {
		return "UserPublicDto [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", role=" + role + ", skillNames=" + skillNames + "]";
	} 
	
}
