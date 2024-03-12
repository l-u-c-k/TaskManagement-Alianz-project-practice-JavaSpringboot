package com.microservicespractise.taskproject.service;

import java.util.List;

import com.microservicespractise.taskproject.payload.TaskDto;

public interface TaskService {

	
	//create a first method to save the task
	public TaskDto saveTask(long userid,TaskDto taskDto); //this method will expect two params one is user_id and second one is dto class
	
	
	//create a method to get the task
	public List<TaskDto> getAllTasks(long userid); //here the returntype should be in List of tasks
	
	//create a method to get an individual task
	public TaskDto getTask(long userid,long taskid);
	
	//to update individual task
	public TaskDto updateTask(long userid, long taskid,TaskDto updatedTaskDto);
	
	
	//create a method for deleting a task
	public void deleteTask(long userid,long taskid);
}
