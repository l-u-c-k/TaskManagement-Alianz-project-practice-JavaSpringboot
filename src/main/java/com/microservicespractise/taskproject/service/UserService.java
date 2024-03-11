package com.microservicespractise.taskproject.service;

import com.microservicespractise.taskproject.payload.UserDto;

public interface UserService {
      public UserDto createUser(UserDto userDto); //the request body we are passing here is UserDto object usetDto is a variable to store that object
      //here whenever we are trying to store the data it will return the actual user object as a return type so thats why we need to give public UserDto
      //after saving this file we will get an error in UserServiceImpl.java because we need to implement createUser method in Impl also
}
