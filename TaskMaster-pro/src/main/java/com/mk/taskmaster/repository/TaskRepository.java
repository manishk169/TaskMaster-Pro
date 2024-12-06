package com.mk.taskmaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mk.taskmaster.entities.Task;
import com.mk.taskmaster.entities.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByProjectId(Long projectId);
	List<Task> findByUser(User user);
	
}
