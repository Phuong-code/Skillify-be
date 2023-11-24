package com.fdmgroup.skillify.dto.user;

import java.util.Objects;

import com.fdmgroup.skillify.enums.RoleType;
import com.fdmgroup.skillify.enums.StatusType;

public class TraineeUpdateDto {
	private String firstName;
	private String lastName;
	private String email;
	private RoleType role;
	private StatusType status;
	
	
	
	public TraineeUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TraineeUpdateDto(String firstName, String lastName, String email, RoleType role, StatusType status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.status = status;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RoleType getRole() {
		return role;
	}
	public void setRole(RoleType role) {
		this.role = role;
	}
	public StatusType getStatus() {
		return status;
	}
	public void setStatus(StatusType status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TraineeUpdateDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", role="
				+ role + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, role, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraineeUpdateDto other = (TraineeUpdateDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && role == other.role && status == other.status;
	}
	
	
	
	
}
