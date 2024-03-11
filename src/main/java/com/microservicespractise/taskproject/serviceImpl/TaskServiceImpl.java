package com.microservicespractise.taskproject.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicespractise.taskproject.entity.Task;
import com.microservicespractise.taskproject.entity.Users;
import com.microservicespractise.taskproject.exception.APIException;
import com.microservicespractise.taskproject.exception.TaskNotFound;
import com.microservicespractise.taskproject.exception.UserNotFound;
import com.microservicespractise.taskproject.payload.TaskDto;
import com.microservicespractise.taskproject.repository.TaskRepository;
import com.microservicespractise.taskproject.repository.UserRepository;
import com.microservicespractise.taskproject.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public TaskDto saveTask(long userid, TaskDto taskDto) {		// TODO Auto-generated method stub
		//before this we need to check whether the user is present and having the userid or not so for this we need to check by usinf the user repository
		//so for this we need to bring the UserRepository above line 20
		Users user = userRepository.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				); //if we are not using get then it will return an optional class so by using get it will return actual data
		//the above line throws the error as not found if the user is not there
		//we need to use TaskRepository to store the task
		//we need to convert the taskDto to entity using model mapper
		
		Task task =  modelMapper.map(taskDto , Task.class); //the map will expect the dto as first param and this dto should be converted to which class should be given as second param
		//after converting to task entity we need to set the user if we founf the user
		task.setUsers(user);
		//after setting the user we are storing the task in the database
		Task savedTask = taskRepository.save(task); //this will return a task 
		return modelMapper.map(savedTask,TaskDto.class); // we need to return the dto so from entity we need to convert to dto
	}

	@Override
	public List<TaskDto> getAllTasks(long userid) {
		// TODO Auto-generated method stub
		//check whether the user is present or not
		userRepository.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		
		//get all the ids of specific user
		List<Task> tasks = taskRepository.findAllByUsersId(userid);
		//in the above we get only the tasks object but we need TaskDto list
		//so we need to use the stream data while returning
		//streams are used for mapping one object with other object in collections
		//here the list of tasks are being converted to the lists of taskDto
		return tasks.stream().map(
				task -> modelMapper.map(task, TaskDto.class)
).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {
		// TODO Auto-generated method stub
		//check whether the user is present or not and if present we need to store the user details in a variable
		Users users = userRepository.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		//here how we are getting the user details in the same way we need to get the tasks also
		Task task = taskRepository.findById(taskid).orElseThrow(
				() -> new TaskNotFound(String.format("Task Id %d not found", taskid))
				);
		//we need to check whether that particular task is belong to the particular user or not
		if(users.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid,userid));
		}
		//if taskid belongs to userid then we need to return the task data by using modelmapper for converting it to dto and dto to entity
		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		// TODO Auto-generated method stub
		//check whether the user is present or not and if present we need to store the user details in a variable
				Users users = userRepository.findById(userid).orElseThrow(
						() -> new UserNotFound(String.format("User Id %d not found", userid))
						);
				//here how we are getting the user details in the same way we need to get the tasks also
				Task task = taskRepository.findById(taskid).orElseThrow(
						() -> new TaskNotFound(String.format("Task Id %d not found", taskid))
						);
				//we need to check whether that particular task is belong to the particular user or not
				if(users.getId() != task.getUsers().getId()) {
					throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid,userid));
				}
				taskRepository.deleteById(taskid); //for deleting the task
		
	}

}
