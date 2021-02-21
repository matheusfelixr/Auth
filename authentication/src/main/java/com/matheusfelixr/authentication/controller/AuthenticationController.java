package com.matheusfelixr.authentication.controller;

import com.matheusfelixr.authentication.model.DTO.AuthenticateRequestDto;
import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import com.matheusfelixr.authentication.security.JwtTokenUtil;
import com.matheusfelixr.authentication.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	@PostMapping(value  = "/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequestDto requestDto) throws Exception {
		
		//Autentica o usuario
		authenticate(requestDto.getUsername(), requestDto.getPassword());
		
		//Busca o userDetails para geracao do token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(requestDto.getUsername());
		//Gera o token
		String token = jwtTokenUtil.generateToken(userDetails);
		
		
		//Busca os dados do usuario
		UserAuthentication userAuthentication = this.userAuthenticationService.findByUserName(requestDto.getUsername()).get();
		
		//Insere o token e as informacoes do usuario na resposta
		return ResponseEntity.ok(token);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}