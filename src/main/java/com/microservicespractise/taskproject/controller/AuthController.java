package com.microservicespractise.taskproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicespractise.taskproject.entity.Users;
import com.microservicespractise.taskproject.payload.JWTAuthResponse;
import com.microservicespractise.taskproject.payload.LoginDto;
import com.microservicespractise.taskproject.payload.UserDto;
import com.microservicespractise.taskproject.security.JwtTokenProvider;
import com.microservicespractise.taskproject.securityconfig.SecurityConfig;
import com.microservicespractise.taskproject.service.UserService;

@RestController //to know that it is a controller
@RequestMapping("/api/auth")
public class AuthController {
	
	//autowiring userService for storing the data in createUser method
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//autowiring jwttokenprovider class
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
  // here we need to create a post method to store the data of the user in DB
	//in this method we need to get the data from the db so we need requestBody json
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		//to save this users we need a userRepository so this save method will pass the data
		//here users is the reference variable-it will hold the Json data
		//so this data was send by this userRepository to store it in database
		//save-this method will helps us to internally create a query to store the data userRepos.save(userDto);
		//now we want to store the data so first we should  userService
//		userService.createUser(userDto); //as it is expecting ResponseEntity we should pass this line as below
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
		//in above we are passing the actual method to be created  and this method will store the data of userdto into users table and return the userdto data and we are setting the httpstatus to created because it will return the status code as success 202
		//actually before storing the user details we need security for password so we need to provide encryption for the password so made the required changes in UserServiceImpl.java file
	}
	
	//create an api for login in the same way we have created for register
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> loginUser(@RequestBody LoginDto loginDto){
		//created an authentication to validate the details
		//but to validate this internally we need UserDetailsService class
		//for loading the user details we should implement the interface of user detail service
		Authentication authentication = 
				authenticationManager.authenticate(
						//here we need to pass one input field
						new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
						);
		//the above authentication is the resultant data of entire principal object
		//the result we are getting from the authentication is called as authentication result and 
		//this result we need to set for the security using security context holder
		//to check whether authentication is valid or not valid try to console it
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		//returning the jwt token
		//get the token we need to autowire the JwtTokenProvider class
		String token = jwtTokenProvider.generateToken(authentication);
//		return new ResponseEntity<>(token,HttpStatus.OK); //but here the token is in a string format but i want it in a JSON format so create a POJO class in payload package
        return ResponseEntity.ok(new JWTAuthResponse(token));	
	}
}
