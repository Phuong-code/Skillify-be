package com.fdmgroup.skillify.dto.user;

import java.util.Objects;

public class TraineePasswordUpdateDto {
	private String password;

	public TraineePasswordUpdateDto(String password) {
		super();
		this.password = password;
	}

	public TraineePasswordUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraineePasswordUpdateDto other = (TraineePasswordUpdateDto) obj;
		return Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "TraineePasswordUpdateDto [password=" + password + "]";
	}

	


	
	
	
}
