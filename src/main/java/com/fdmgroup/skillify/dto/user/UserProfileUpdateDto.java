package com.fdmgroup.skillify.dto.user;

import java.util.UUID;

public class UserProfileUpdateDto {

	private UUID id;
    private String email;
    private String firstName;
    private String lastName;

	public UserProfileUpdateDto() {
		super();
	}
	
	public UserProfileUpdateDto(UUID id, String email, String firstName, String lastName) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
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

	@Override
	public String toString() {
		return "UserProfileUpdateDto [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}
	
}
