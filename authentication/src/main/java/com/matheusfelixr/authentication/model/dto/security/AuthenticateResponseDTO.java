package com.matheusfelixr.authentication.model.dto.security;

import lombok.Data;

@Data
public class AuthenticateResponseDTO {

	private String userName;
	private String token;
	private Boolean changePassword;
	private Boolean isAdmin;

	public AuthenticateResponseDTO(String userName, String token, Boolean changePassword, Boolean isAdmin) {
		this.userName = userName;
		this.token = token;
		this.changePassword = changePassword;
		this.isAdmin = isAdmin;
	}

	public AuthenticateResponseDTO() {
	}
}
