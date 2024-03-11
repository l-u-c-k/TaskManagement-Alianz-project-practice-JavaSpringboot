package com.microservicespractise.taskproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data   //by giving this we no need to create getters and setters manually
@AllArgsConstructor
@NoArgsConstructor //similar to default constructor
@Entity
@Table(name="users", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"email"})
})
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //here id is the primary key so use @Id and it should be generated automatically so give @ generated value
    //if we want to change the column name any time we need to give @Column and the name field should not have null values so give nullable as false
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "email", nullable = false)
    private String email; //this email should be unique so add uniqueContraints in the @Table
	@Column(name = "password", nullable = false)
	private String password;
    
}
