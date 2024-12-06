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

import com.mk.taskmaster.entities.Client;
import com.mk.taskmaster.entities.Project;
import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.service.ClientService;
import com.mk.taskmaster.service.ProjectService;
import com.mk.taskmaster.service.UserService;

@Controller
@RequestMapping("/user")
public class ProjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    @ModelAttribute
    public void addUserAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("fullName", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("phonenumber", user.getPhoneNumber());
            model.addAttribute("clients", clientService.getClientsByUser(user)); // Fetch clients for current user
        }
    }

    
    @GetMapping("/projects")
    public String showProjects(Model  model) {
    	// Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        // Fetch the projects associated with the current user
        List<Project> userProjects = projectService.getProjectsByUser(user);

        // Add the projects to the model to be displayed in the view
        model.addAttribute("projects", userProjects);

        return "Project/projects"; //
    }
    
    @GetMapping("/projects/addProject")
    public String showAddProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "Project/add-project";
    }

    @PostMapping("/projects/addProject/add")
    public String addProject(@ModelAttribute Project project, Long clientId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        // Fetch the client based on the selected clientId
        Client client = clientService.getClientById(clientId);

        // Set the current user (freelancer) and the selected client to the project
        project.setUser(user);
        project.setClient(client);

        // Save the project
        projectService.addProject(project);

        model.addAttribute("message", "Project added successfully.");
        return "redirect:/user/projects"; // Redirect to the projects page
    }
}
	