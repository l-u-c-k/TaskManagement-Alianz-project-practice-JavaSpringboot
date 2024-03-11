package com.microservicespractise.taskproject.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservicespractise.taskproject.entity.Users;
import com.microservicespractise.taskproject.exception.UserNotFound;
import com.microservicespractise.taskproject.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		// TODO Auto-generated method stub
		Users user = userRepository.findByEmail(email).orElseThrow(
				() -> new UserNotFound(String.format("User with email : %d is not found", email))
				);
		//in above we have loaded the user and now we need to return this user
		//in the format of users object
		//this will expect input args as string(email),string(password from userdetails),granted authorities can be set as below
		//here we are using Set because one user can have many roles like admin and soon
		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_ADMIN");
		return new User(user.getEmail(),user.getPassword(),userAuthorities(roles));
	}

	//the third argument for line 33 is a collection of granted authorities
	//so create a collection as below
	private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles){
		return roles.stream().map(
				role -> new SimpleGrantedAuthority(role)
				).collect(Collectors.toList());
		
	}
}
