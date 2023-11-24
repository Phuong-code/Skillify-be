package com.fdmgroup.skillify.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public class RefreshTokenRequestDto {
    @NotEmpty
    private String refreshToken;

	public RefreshTokenRequestDto() {
		super();
	}

	public RefreshTokenRequestDto(@NotEmpty String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "RefreshTokenRequestDto [refreshToken=" + refreshToken + "]";
	}
    
}
