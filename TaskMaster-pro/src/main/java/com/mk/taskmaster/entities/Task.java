package com.mk.taskmaster.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // Assuming the foreign key column is named user_id
    private User user;

    private String task_name;

    private String task_description;

    @Enumerated(EnumType.STRING)
    private TaskStatus task_status;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority; // Ensure Priority is properly defined as an enum

    private LocalDate reminderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public TaskStatus getTask_status() {
        return task_status;
    }

    public void setTask_status(TaskStatus task_status) {
        this.task_status = task_status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() { // Adjusted to use enum
        return priority;
    }

    public void setPriority(Priority priority) { // Adjusted to use enum
        this.priority = priority;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
