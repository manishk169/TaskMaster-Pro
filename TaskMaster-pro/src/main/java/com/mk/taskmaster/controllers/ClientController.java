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
import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.service.ClientService;
import com.mk.taskmaster.service.UserService;

@Controller
@RequestMapping("/user")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
    public void addUserAttributes(Model model) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by email (serving as username)
        User user = userService.findByUsername(currentUsername);

        // Pass common user details to the model
        if (user != null) {
        	
            model.addAttribute("fullName", user.getName()); // Full name of the user
            model.addAttribute("email", user.getEmail());   // Email (username)
            model.addAttribute("phonenumber", user.getPhoneNumber()); // Phone number (if applicable)
        }
    }

	@GetMapping("/clients")
    public String showClientsList(Authentication authentication, Model model) {
        // Get the currently authenticated user
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        // Get all clients associated with the current user
        List<Client> clients = clientService.getClientsByUser(currentUser);

        // Add clients to the model
        model.addAttribute("clients", clients);

        System.out.println(clients);

        return "Client/clients";  // This refers to clients.html
    }

	
	@GetMapping("/clients/addClient")
	public String showClientForm(Model model) {
		model.addAttribute("client", new Client());
		return "Client/add-client";
	}
	

	 @PostMapping("/clients/addClient/add")
	    public String addClient(@ModelAttribute Client client, Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String currentUsername = authentication.getName();
	        User user = userService.findByUsername(currentUsername);

	        // Associate the client with the current user
	        client.setUser(user);
	        clientService.addClient(client);

	        model.addAttribute("message", "Client added successfully.");
	        return "redirect:/user/clients";  // Redirect to the clients page
	    }
	
	
}
