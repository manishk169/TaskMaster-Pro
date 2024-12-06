package com.mk.taskmaster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.service.UserService;



@Controller
public class HomeController {

	/*
	 * @GetMapping("/registration") public String registerpage() { return
	 * "register"; }
	 */

	@GetMapping("/aboutUs")
	public String aboutuspage() {
		return "about-us";
	}
	
	@GetMapping("/contactUs")
	public String contactuspage() {
		return "contact-us";
	}

	@GetMapping("/login")
	public String loginpage() {
		return "login";
	}
	
	//register-login requests
	@Autowired
	private UserService userService;

	@GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
	
	@PostMapping("/registration/saveUser")
	public String registerNewUser(@ModelAttribute User user, Model model) {
		userService.registerUser(user);
		model.addAttribute("success", "true");
		return "register";
	}
	
	

	
}

