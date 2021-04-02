package com.matheusfelixr.authentication.model.dto.security;

import lombok.Data;

@Data
public class CreateUserRequestDTO {

	private String username;

	private String email;

	public CreateUserRequestDTO() {
	}

	public CreateUserRequestDTO(String username, String email) {
		this.username = username;
		this.email = email;
	}
}
