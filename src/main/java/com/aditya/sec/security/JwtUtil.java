package com.aditya.sec.security;

import java.nio.charset.MalformedInputException;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private String jwtExpirtionDate;

	public String generateToken(Authentication authentication) {
		String userName = authentication.getName();

		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + jwtExpirtionDate);

		String token = Jwts.builder().subject(userName).issuedAt(currentDate).expiration(expirationDate)
				.signWith(keys()).compact();

		return token;
	}

	public Key keys() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// Get Username from token
	public String getUserName(String token) {
		return Jwts.parser().verifyWith((SecretKey) keys()).build().parseSignedClaims(token).getPayload().getSubject();

	}

	// Validate User from token
	public boolean validateToken(String token) {
		Jwts.parser().verifyWith((SecretKey) keys()).build().parse(token);
		return true;
	}

}
