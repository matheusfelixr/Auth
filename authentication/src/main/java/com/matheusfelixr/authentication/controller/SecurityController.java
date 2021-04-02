package com.matheusfelixr.authentication.controller;

import com.matheusfelixr.authentication.model.dto.MessageDTO;
import com.matheusfelixr.authentication.model.dto.config.ResponseApi;
import com.matheusfelixr.authentication.model.dto.security.*;
import com.matheusfelixr.authentication.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/security")
public class SecurityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

	@Autowired
	private SecurityService securityService;

	@PostMapping(value  = "/authenticate")
	public ResponseEntity<ResponseApi<AuthenticateResponseDTO>> authenticate(@RequestBody AuthenticateRequestDTO authenticateRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.debug("Inicio processo de autenticacao.");
		ResponseApi<AuthenticateResponseDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.authenticate(authenticateRequestDTO,httpServletRequest ));
			LOGGER.debug("Autenticacao realizada com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar autenticar");
			List<String> errors = Arrays.asList("Erro inesperado ao tentar autenticar");
			response.setErrors(errors);
			return ResponseEntity.ok(response);
		}
	}

	@PostMapping(value  = "/reset-password")
	public ResponseEntity<ResponseApi<MessageDTO>> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.debug("Inicio processo de reset de senha.");
		ResponseApi<MessageDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.resetPassword(resetPasswordRequestDTO.getUsername().trim(),httpServletRequest));
			LOGGER.debug("Reset de senha realizado com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar resetar senha");
			List<String> errors = Arrays.asList("Erro inesperado ao tentar resetar senha");
			response.setErrors(errors);
			return ResponseEntity.ok(response);
		}
	}


	@PostMapping(value  = "/create-user")
	public ResponseEntity<ResponseApi<MessageDTO>> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) throws Exception {
		LOGGER.debug("Inicio processo de criação de usuario.");
		ResponseApi<MessageDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.createUser(createUserRequestDTO));
			LOGGER.debug("Processo de criação de usuario realizado com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar criar novo usuário");
			List<String> errors = Arrays.asList("Erro inesperado ao tentar criar novo usuário");
			response.setErrors(errors);
			return ResponseEntity.ok(response);
		}
	}

	@PostMapping(value  = "/new-password")
	public ResponseEntity<ResponseApi<AuthenticateResponseDTO>> newPassword(@RequestBody NewPasswordRequestDTO newPasswordRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.debug("Inicio processo de autenticacao.");
		ResponseApi<AuthenticateResponseDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.newPassword(newPasswordRequestDTO, httpServletRequest));
			LOGGER.debug("Autenticacao realizada com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar autenticar");
			List<String> errors = Arrays.asList("Erro inesperado ao tentar autenticar");
			response.setErrors(errors);
			return ResponseEntity.ok(response);
		}
	}
}