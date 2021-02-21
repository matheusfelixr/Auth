package com.matheusfelixr.authentication.model.DTO;

import lombok.Data;

@Data
public class AuthenticateRequestDto {
	
	private String username;
	private String password;
	
}
