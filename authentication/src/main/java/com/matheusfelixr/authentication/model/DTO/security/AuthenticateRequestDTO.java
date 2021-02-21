package com.matheusfelixr.authentication.model.DTO.security;

import lombok.Data;

@Data
public class AuthenticateRequestDTO {
	
	private String username;
	private String password;
	
}
