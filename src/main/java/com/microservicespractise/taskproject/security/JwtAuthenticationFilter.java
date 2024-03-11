package com.microservicespractise.taskproject.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//this class will extend onceperrequest filter
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	//to use validateToken method we need to autowire that method
	@Autowired
	private JwtTokenProvider jwtTokenProvider; 
	
	//autowire customdetailsservice class
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	//here we need to filter every request
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {		// TODO Auto-generated method stub
		
		//here first we need to get the token from headers
		String token = getToken(request);
		//second we need to check whether the token is either valid or not
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			//if it is valid we need to load the user by using loadUserByUsername method from CustomUserDetailsService.java
			//for below loadUserByusername method it requires email as a param so we need to get the email using a method called
			//getEmailFromToken() which was present in JwtTokenProvider class
			String email = jwtTokenProvider.getEmailFromToken(token);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
		//if the token is valid we will load the user and setAuthentication
		//authenticate username and password using UsernamePasswordAuthenticationToken
		UsernamePasswordAuthenticationToken authentication = 
				//it requires three paramters 
				//user principal object,credentials-here we not sharing any so null,get the authorities
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		//set the security
		SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		//after setting the authentication set the filter
		filterChain.doFilter(request, response);
	}
	
	//this method will take the input as httpservletRequest 
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		//check whether this header has some value or not
		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7,token.length());
		}
		//if that token is not having any text or if text is there and not started with bearer then that token doesnt belongs to us so we need to return Null
		return null;
	}

}
