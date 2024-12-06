package com.mk.taskmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.taskmaster.entities.Project;
import com.mk.taskmaster.entities.Task;
import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.repository.ProjectRepository;
import com.mk.taskmaster.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    // Fetch all tasks associated with the current user
    public List<Task> getAllTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Transactional
    public void saveTask(Task task) {
        Project project = task.getProject();
        if (project != null && project.getId() == null) {
            projectRepository.save(project);
        }
        taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }
}
