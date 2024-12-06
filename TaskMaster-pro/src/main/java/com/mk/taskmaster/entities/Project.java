package com.mk.taskmaster.entities;

import jakarta.persistence.*;


@Entity
@Table (name = "projects")
public class Project {
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure this is set
    private Long id;

	
	@Column
	private String projectName;
	
	@Column(name = "description") // Make sure this is defined in your Project entity
	private String projectDescription;

	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client; // Associated Client
	
	 @ManyToOne
	 @JoinColumn(name = "freelancer_id", nullable = false)
	 private User user; // Associated freelancer (user)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
