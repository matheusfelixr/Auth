package com.matheusfelixr.authentication.model.DTO.security;

import lombok.Data;

@Data
public class AuthenticateResponseDTO {
	
	private String userName;
	private String token;

	public AuthenticateResponseDTO(String userName, String token) {
		this.userName = userName;
		this.token = token;
	}

	public AuthenticateResponseDTO() {
	}
}
