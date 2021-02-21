package com.matheusfelixr.authentication.service;

import com.matheusfelixr.authentication.model.DTO.security.AuthenticateRequestDTO;
import com.matheusfelixr.authentication.model.DTO.security.AuthenticateResponseDTO;
import com.matheusfelixr.authentication.model.DTO.security.ResetPasswordResponseDTO;
import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import com.matheusfelixr.authentication.security.JwtTokenUtil;
import com.matheusfelixr.authentication.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.util.Optional;

@Component
public class SecurityService implements UserDetailsService {
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	    	
    	Optional<UserAuthentication> userAuthentication = this.userAuthenticationService.findByUserName(username);
    	
    	if(!userAuthentication.isPresent()) {
            throw new UsernameNotFoundException(username + " nao encontrado");
        }
    	
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                userAuthentication.get().getUserName(),
                userAuthentication.get().getPassword(),
                AuthorityUtils.createAuthorityList(userAuthentication.get().getRoles())
        );
		return userDetails;
    }


    public AuthenticateResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO) throws Exception {
        try {
            //Autentica o usuario
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequestDTO.getUsername(), authenticateRequestDTO.getPassword()));
            //Busca o userDetails para geracao do token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticateRequestDTO.getUsername());
            //Gera o token
            String token = jwtTokenUtil.generateToken(userDetails);
            //Busca os dados do usuario
            UserAuthentication userAuthentication = this.userAuthenticationService.findByUserName(authenticateRequestDTO.getUsername()).get();

            return new AuthenticateResponseDTO(userAuthentication.getUserName(), token);
        } catch (DisabledException e) {
            throw new ValidationException("Usuário desabilitado");
        } catch (BadCredentialsException e) {
            throw new ValidationException("Senha invalida");
        }
    }

    public ResetPasswordResponseDTO resetPassword(String userName) throws Exception {
        String password = Password.generatePasswordInt(5);
        UserAuthentication userAuthentication = userAuthenticationService.modifyPassword(userName, password);
        emailService.resetPassword(userAuthentication, password);
        return new ResetPasswordResponseDTO ("Foi enviado uma nova senha para o seu E-mail");
    }
}