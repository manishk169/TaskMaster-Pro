package com.mk.taskmaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.taskmaster.entities.Client;
import com.mk.taskmaster.entities.Project;
import com.mk.taskmaster.entities.User;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	List<Project> findByUser(User user); // Get projects of the freelancer
    List<Project> findByClient(Client client); // Get projects of a specific client

}
