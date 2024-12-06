package com.mk.taskmaster.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.taskmaster.entities.Project;
import com.mk.taskmaster.entities.Task;
import com.mk.taskmaster.entities.TaskStatus;
import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.service.ProjectService;
import com.mk.taskmaster.service.TaskService;
import com.mk.taskmaster.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;
    
    
    // Global method to add authenticated user details to every view
    @ModelAttribute
    public void addUserAttributes(Model model) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by email (serving as username)
        User user = userService.findByUsername(currentUsername);

        // Pass common user details to the model
        if (user != null) {
        	 // Pass the user object to the model
            model.addAttribute("user", user);
            model.addAttribute("fullName", user.getName()); // Full name of the user
            model.addAttribute("email", user.getEmail());   // Email (username)
            model.addAttribute("phonenumber", user.getPhoneNumber()); // Phone number (if applicable)
        }
    }

    // Mapping for user dashboard
    @GetMapping("/userDashboard")
    public String showUserDashboard(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        if (user != null) {
            // Fetch all tasks created by the logged-in user
            List<Task> allTasks = taskService.getAllTasksByUser(user);

            // Filter tasks by status
            long tasksCompletedCount = allTasks.stream()
                    .filter(task -> task.getTask_status() == TaskStatus.COMPLETED)
                    .count();

            model.addAttribute("tasksCompletedCount", tasksCompletedCount);
            
            
            
            // Fetch all projects associated with the user (assuming a method exists in ProjectService)
            List<Project> userProjects = projectService.getProjectsByUser(user);

            // Count the number of projects
            long projectsCount = userProjects.size();

            model.addAttribute("projectsCount", projectsCount);
        }
    	return "user-dashboard";  // View name for the user dashboard page
        
    }
    
    @GetMapping("/profile")
    public String userProfilepage(Model model) {
        return "profile";  // View name for the profile page
    }

    
    @PostMapping("/profile/updateProfile")
    public String updateProfile(@ModelAttribute("user") User updatedUser) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the current user by email (username)
        User currentUser = userService.findByUsername(currentUsername);

        // Update the current user's details
        currentUser.setName(updatedUser.getName());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());

        // You can allow email updates, but typically this could cause authentication issues
        // currentUser.setEmail(updatedUser.getEmail());

        // Save the updated user details
        userService.updateUser(currentUser);

        // Redirect to the profile page with a success message
        return "redirect:/user/profile?success=true";
    }

    
    
	/* User dashboardUser Pages Mapping*/

        
    
    

}
