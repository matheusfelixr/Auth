package com.matheusfelixr.authentication.controller;

import com.matheusfelixr.authentication.model.DTO.security.AuthenticateRequestDTO;
import com.matheusfelixr.authentication.model.DTO.config.ResponseApi;
import com.matheusfelixr.authentication.model.DTO.security.AuthenticateResponseDTO;
import com.matheusfelixr.authentication.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<ResponseApi<AuthenticateResponseDTO>> authenticate(@RequestBody AuthenticateRequestDTO authenticateRequestDTO) throws Exception {
		LOGGER.debug("Inicio processo de autenticacao.");
		ResponseApi<AuthenticateResponseDTO> response = new ResponseApi<>();
		try {
			response.setData(this.securityService.authenticate(authenticateRequestDTO));
			LOGGER.debug("Autenticacao realizada com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar autenticar");
			List<String> erros = Arrays.asList("Erro inesperado ao tentar autenticar");
			response.setErrors(erros);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}


}