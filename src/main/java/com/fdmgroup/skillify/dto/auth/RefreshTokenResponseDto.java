package com.fdmgroup.skillify.dto.auth;

public class RefreshTokenResponseDto {
    private String accessToken;

	public RefreshTokenResponseDto() {
		super();
	}

	public RefreshTokenResponseDto(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "RefreshTokenResponseDto [accessToken=" + accessToken + "]";
	}
    
}
