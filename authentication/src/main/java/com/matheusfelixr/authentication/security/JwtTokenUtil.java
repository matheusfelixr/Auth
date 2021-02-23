package com.matheusfelixr.authentication.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -2343485165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);


	@Value("${jwt.secret}")
	private String secret;

	//retorna o username do token jwt 
	public String getUsernameFromToken(String token) {
		String ret = getClaimFromToken(token, Claims::getSubject);
		return ret;
	}

	//retorna expiration date do token jwt 
	public Date getExpirationDateFromToken(String token) {
		Date ret = getClaimFromToken(token, Claims::getExpiration);
		return ret;
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	//para retornar qualquer informação do token nos iremos precisar da secret key
	private Claims getAllClaimsFromToken(String token) {
		Claims ret = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return ret;
	}

	//Verifica que o token expirou
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	//gera token para user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String ret = doGenerateToken(claims, userDetails.getUsername());
		return ret;
	}

	//Cria o token e define tempo de expiração pra ele
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 99999999))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	//valida o token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}