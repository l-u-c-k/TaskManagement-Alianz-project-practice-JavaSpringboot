package com.microservicespractise.taskproject.security;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.microservicespractise.taskproject.exception.APIException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.KeyPair;

@Component
public class JwtTokenProvider {
	 
//	private String secretKey="JWTSecretKey";
	 private java.security.KeyPair keyPair;
	 
	 public JwtTokenProvider() throws NoSuchAlgorithmException {
		 //initialise keypair using RSA algorithm
		 KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		 //we can adjust the key size as needed
		 keyPairGenerator.initialize(2048);
		 this.keyPair = keyPairGenerator.generateKeyPair();
	 }

	//create a method for creating a token
	//this will take an argument of authentication
	public String generateToken(Authentication authentication) {
		//we want email from this authentication so get it by using getName
		String email = authentication.getName();
		//the token that we are generating should have a current time and expiretime
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + 3600000);//in milliseconds 3600000 is equal to one hour
		//generating a token
		String token = Jwts.builder()
				.setSubject(email)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
	            .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)   //to set that we only should decode the token then we have to set security it requires two args one is algorithm and second is key
		         .compact();
		return token;		
	}
	
	//how to get the value of email from this token
	public String getEmailFromToken(String token) {
		//parse this token which is of string to Json format		Claims claims = Jwts.parser().setSigningKey("JWTSecretKey").parseClaimsJws(token).getBody();
		Jws<Claims> jws=Jwts.parser().setSigningKey(keyPair.getPublic()).build().parseClaimsJws(token);
		Claims claims = jws.getBody();
		
		//return the subject
		return claims.getSubject(); //here the subject will be an email
	}
	
	
	//checking whether token is valid or not so return type should be boolean
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			.setSigningKey(keyPair.getPublic())
			.build()
			.parseClaimsJws(token);
			return true;
		}
		catch(Exception e) {
			throw new APIException("Token issue: "+e.getMessage());
		}
			
	}
}
