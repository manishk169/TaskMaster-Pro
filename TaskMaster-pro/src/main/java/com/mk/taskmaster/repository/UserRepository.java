package com.mk.taskmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.taskmaster.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	
}
