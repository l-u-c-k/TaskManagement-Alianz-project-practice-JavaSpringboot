package com.microservicespractise.taskproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicespractise.taskproject.payload.TaskDto;
import com.microservicespractise.taskproject.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
	
	//annotating task service
	@Autowired
	private TaskService taskService;
	
	//creating a method to save or store the task details
	//ie.save the task
	
	//this method is invoked when the api is hitted
	@PostMapping("/{userid}/tasks")
	public ResponseEntity<TaskDto> saveTask(
			//this method will expect two things
			//1)path variable-in below the name="userid" is the value that we are getting from api ie.from postmapping field
			//2)requestbody of taskdto
			@PathVariable(name="userid") long userid,
			@RequestBody TaskDto taskDto
			){
		
		//here we are going to save the data using taskservice so we need to annotate taskservice
	  //  taskService.saveTask(userid, taskDto); //here we are assigning the task ietaskDto for the particular user
		//the above is replaced by using responseentity
		return new ResponseEntity<>(taskService.saveTask(userid, taskDto),HttpStatus.CREATED);
	}
	
	//get all tasks
	@GetMapping("/{userid}/tasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(
			//this will expect only pathvariable
			@PathVariable(name="userid") long userid
			){
		return new ResponseEntity<>(taskService.getAllTasks(userid),HttpStatus.OK);
		
	}
	
	
	//get individual task
	@GetMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<TaskDto> getTask(
			//it requires two pathvariables ie.userid and taskid
			@PathVariable(name="userid") long userid,
			@PathVariable(name="taskid") long taskid
			){
		return new ResponseEntity<>(taskService.getTask(userid, taskid),HttpStatus.OK);
	}
	
	//delete individual task
	@DeleteMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<String> deleteTask(
			//it requires two pathvariables ie.userid and taskid
			@PathVariable(name="userid") long userid,
			@PathVariable(name="taskid") long taskid
			){
		taskService.deleteTask(userid, taskid);
		return new ResponseEntity<>("Task deleted successfully!",HttpStatus.OK);
	}


}
