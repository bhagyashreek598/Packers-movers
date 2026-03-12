package com.backend.security;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component //to declare a spring bean 
@Slf4j
public class JwtUtils {
	@Value("${jwt.expiration.time}")
	private long jwtExpirationTime;
	@Value("${jwt.secret}")
	private String jwtSecret;
	private SecretKey secretKey;
	
	@PostConstruct
	public void myInit()
	{
		log.info("****** creating secret key {} {} ",jwtSecret,jwtExpirationTime);
		secretKey=Keys.hmacShaKeyFor(jwtSecret.getBytes());		
	}
	//create JWT - header , payload, signature
	public String generateToken(User principal) {
		//iat  
		Date now=new Date();
		//exp
		Date expiresAt=new Date(now.getTime()+jwtExpirationTime);
		return Jwts.builder() //creates a builder fro JWT creation
				.subject(principal.getEmail()) //setting subject
				.issuedAt(now) //iat
				.expiration(expiresAt) //exp
				//custom claims - user id & user role
				.claims(Map.of("user_id", principal.getId()
						, "user_role", "ROLE_"+principal.getUserRole()))
				.signWith(secretKey)//sign the JWT
				.compact();
				
	}
	public Claims validateToken(String jwt) {
		return Jwts.parser() //attach a parser
				.verifyWith(secretKey)
				.build() //builds JwtsParser
				.parseSignedClaims(jwt)
				.getPayload();
		
	}
	// JwtUtils.java mein ye update karein
	public String generateToken(UserPrincipal principal) {
	    Date now = new Date();
	    Date expiresAt = new Date(now.getTime() + jwtExpirationTime);
	    
	    return Jwts.builder()
	            .subject(principal.getUsername()) // Email
	            .issuedAt(now)
	            .expiration(expiresAt)
	            // Custom claims: principal se ID aur Role nikalein
	            .claims(Map.of(
	                "user_id", principal.getUserId(), 
	                "user_role",  "ROLE_" +principal.getRoleName() // Role string (e.g., "CUSTOMER")
	            ))
	            .signWith(secretKey)
	            .compact();
	}

}
