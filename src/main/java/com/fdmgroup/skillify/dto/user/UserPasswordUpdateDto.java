package com.fdmgroup.skillify.dto.user;

import java.util.UUID;

public class UserPasswordUpdateDto {
	
	private UUID id;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	public UserPasswordUpdateDto() {
		super();
	}

	public UserPasswordUpdateDto(UUID id, String oldPassword, String newPassword, String confirmPassword) {
		super();
		this.id = id;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UserPasswordUpdateDto [id=" + id + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	
}
