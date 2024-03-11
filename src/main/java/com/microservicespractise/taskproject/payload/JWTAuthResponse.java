package com.microservicespractise.taskproject.payload;

import lombok.Getter;

@Getter

public class JWTAuthResponse {
  private String token;
  private String tokenType = "Bearer"; //here Bearer means JSON
  
  public JWTAuthResponse(String token) {
	  this.token=token;
  }
}
