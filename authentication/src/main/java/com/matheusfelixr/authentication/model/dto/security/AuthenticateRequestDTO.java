package com.matheusfelixr.authentication.model.dto.security;

import lombok.Data;

@Data
public class AuthenticateRequestDTO {
	
	private String username;
	private String password;

	public AuthenticateRequestDTO() {
	}

	public AuthenticateRequestDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
