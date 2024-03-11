package com.microservicespractise.taskproject.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservicespractise.taskproject.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	//internally authenticationManager will validate the user details with encrypted password so for this we need to create a bean
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
  //creating 2 beans
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		//bean should return httprequest
		http
		.csrf().disable()//here we are disabling crosssite request frozree because we are using third party APIs only
		//if we doesnt disable then we will get 403 forbidden error
		.authorizeRequests()
//		.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
		//we can mention post if needed if we want to allow only post 
		.requestMatchers("/api/auth/**").permitAll()
		.anyRequest()
		.authenticated();
		//by using addFilterBefore every request should be filter before sending the request 
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManage(
			//this will take the input parameter of authentication configuration
			AuthenticationConfiguration authenticationConfiguration
			) throws Exception {
		//here we need to return getAuthenticationManager which is present in authenticationConfiguration
		return authenticationConfiguration.getAuthenticationManager();
		
	}
}
