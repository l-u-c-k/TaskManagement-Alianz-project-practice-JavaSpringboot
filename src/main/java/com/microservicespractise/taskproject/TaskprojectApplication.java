package com.microservicespractise.taskproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskprojectApplication.class, args);
	}

	
	//creating a bean entity for model mapper
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
		
	}
}
