package com.fdmgroup.skillify.dto.user;

import com.fdmgroup.skillify.enums.RoleType;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

public class UserRegisterDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private RoleType role;

    private String firstName;
    private String lastName;
    
	public UserRegisterDto() {
		super();
	}

	public UserRegisterDto(@NotEmpty String email, @NotEmpty String password, @NotEmpty RoleType role, String firstName,
			String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
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
		return "UserRegisterDto [email=" + email + ", password=" + password + ", role=" + role + ", firstName="
				+ firstName + ", lastName=" + lastName + "]";
	}
    
}
