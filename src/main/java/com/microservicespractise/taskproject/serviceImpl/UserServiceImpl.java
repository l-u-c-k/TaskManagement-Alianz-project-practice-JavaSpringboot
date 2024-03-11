package com.microservicespractise.taskproject.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservicespractise.taskproject.entity.Users;
import com.microservicespractise.taskproject.payload.UserDto;
import com.microservicespractise.taskproject.repository.UserRepository;
import com.microservicespractise.taskproject.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//we need to provide security for password so we need to encrpyt it	private PasswordEncoder;
	@Override
	public UserDto createUser(UserDto userDto) {		// TODO Auto-generated method stub
		// userDto is not an entity of Users entity those both are not equal
//		userRepository.save(userDto); //here userRepository is being created for the referenece of user so we should not give userDto
		//so for sending this userDto we need to do some typecasting ie.we should convert this userDto into users data for doing this we are creating one method below as userDtoToEntity
		//we need to encode the password field before saving it in DB
		//to acheive this do the encode as below
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); //so when user registers by giving the details at that time itseld the password will stored by decrypting
		Users user = userDtoToEntity(userDto);
		Users savedUser = userRepository.save(user); //converted userDto to Users
		//here this savedUser object should be converted to UserDto because the return type is UserDto
		//so for converting we are creating a separate method entityToUserDto
		return entityToUserDto(savedUser);
	}

	private Users userDtoToEntity(UserDto userDto) {
		//create a user object
		Users users = new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		return users;
	}
	
	private UserDto entityToUserDto(Users savedUser)  //it will expect the original user data so we need to pass user as an argument
	{
		//create a UserDto object
		UserDto userDto = new UserDto();
		//here we need to set the id also since we are passing original data
		userDto.setId(savedUser.getId());
		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setPassword(savedUser.getPassword());
		return userDto;
	}
}
