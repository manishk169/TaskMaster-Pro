package com.mk.taskmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.taskmaster.entities.Client;
import com.mk.taskmaster.entities.Project;
import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	public ProjectRepository projectRepository;
	
	
	public Project addProject(Project project) {
		return projectRepository.save(project);
        
    }
	
	
	
	
	//getting projects list 
	 public List<Project> getProjectsByUser(User user) {
	        return projectRepository.findByUser(user);
	 }

	 //retrieves the list of clients  for adding project
	 public List<Project> getProjectsByClient(Client client) {
	        return projectRepository.findByClient(client);
	 }
	
}
