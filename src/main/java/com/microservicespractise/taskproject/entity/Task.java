package com.microservicespractise.taskproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
	 @Column(name = "taskname", nullable = false)
     private String taskname;
	 
	 //for user id we need to store the entire object of the users
	 // now we need to map this task table with the user table to get the user id for this we are having 4 methods like many-to-many,many-to-one an soon on here we will use many-to-one mapping because we can assign many tasks to one user so we need to give maytoone
	 @ManyToOne(fetch = FetchType.LAZY) //here we are using fetch method and lazy because here we are trying to use the entity of other class called users so when using it when we are trying to fetch the data of task here we only want id and taskname but not the details of user then we will use lazy viceversa eager method
	 @JoinColumn(name = "users_id") //here join column is the column name we want to assign ie.user_id
	 private Users users;
     
}
