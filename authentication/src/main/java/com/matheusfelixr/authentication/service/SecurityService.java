package com.matheusfelixr.authentication.service;

import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityService implements UserDetailsService {
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
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
}