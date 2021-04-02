package com.matheusfelixr.authentication.model.dto.security;

import lombok.Data;

@Data
public class NewPasswordRequestDTO {

	private String userName;
	private String password;

	public NewPasswordRequestDTO() {
	}

	public NewPasswordRequestDTO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
}
