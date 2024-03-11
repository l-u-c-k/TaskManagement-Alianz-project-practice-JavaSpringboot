package com.microservicespractise.taskproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) //404 error
public class UserNotFound extends RuntimeException{

	//we can set the own message because of using RuntimeException
	private String message;
	
	public UserNotFound(String message) {
		//we will set that message to superclass
		//this super class will return the same message whatever we are providing while if we are getting any error
		super(message);
		//assign the same message to current class as well
		this.message = message;
	}
}
//use this class for showing the manual exception in TaskServiceImpl class
