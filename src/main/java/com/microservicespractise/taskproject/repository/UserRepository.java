package com.microservicespractise.taskproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicespractise.taskproject.entity.Users;


//here jparepository has the inbuilt functions to perform all the crud operations
public interface UserRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByEmail(String email);

}
