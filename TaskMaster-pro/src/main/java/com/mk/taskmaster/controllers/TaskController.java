package com.mk.taskmaster.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mk.taskmaster.entities.Project;
import com.mk.taskmaster.entities.Task;
import com.mk.taskmaster.entities.TaskStatus;
import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.service.ProjectService;
import com.mk.taskmaster.service.TaskService;
import com.mk.taskmaster.service.UserService;

@Controller
@RequestMapping("/user")
public class TaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

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
        }
    }

    @GetMapping("/tasks")
    public String showTasks(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        if (user != null) {
            // Fetch all tasks created by the logged-in user
            List<Task> allTasks = taskService.getAllTasksByUser(user);

            // Filter tasks by status
            List<Task> completedTasks = allTasks.stream()
                                                .filter(task -> task.getTask_status() == TaskStatus.COMPLETED)
                                                .toList();
            List<Task> otherTasks = allTasks.stream()
                                            .filter(task -> task.getTask_status() != TaskStatus.COMPLETED)
                                            .toList();
            
            long tasksCompletedCount = allTasks.stream()
                    .filter(task -> task.getTask_status() == TaskStatus.COMPLETED)
                    .count();

            // Add filtered task lists to the model
            model.addAttribute("completedTasks", completedTasks);
            model.addAttribute("otherTasks", otherTasks);
            model.addAttribute("tasksCompletedCount", tasksCompletedCount);

        }

        return "Task/tasks";
    }

    @GetMapping("/tasks/addtask/{projectId}")
    public String showTaskForm(@PathVariable Long projectId, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("projectId", projectId);
        return "Task/add-task-form";
    }


    @PostMapping("/tasks/add/{projectId}")
    public String saveTask(@ModelAttribute Task task, @PathVariable Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        if (currentUser != null) {
            task.setUser(currentUser);
            Project project = taskService.getProjectById(projectId);

            if (project != null) {
                task.setProject(project);
                taskService.saveTask(task);
            }
        }
        return "redirect:/user/tasks";
    }

    @GetMapping("/tasks/markcompleted/{id}")
    public String markTaskAsCompleted(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            task.setTask_status(TaskStatus.COMPLETED);
            taskService.saveTask(task);
        }
        return "redirect:/user/tasks";
    }
    
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/user/tasks";
    }

}
