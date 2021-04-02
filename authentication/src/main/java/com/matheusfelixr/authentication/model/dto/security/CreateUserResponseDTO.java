package com.matheusfelixr.authentication.model.dto.security;

import lombok.Data;

@Data
public class CreateUserResponseDTO {

	private String message;

	public CreateUserResponseDTO(String message) {
		this.message = message;
	}
}
