package com.mk.taskmaster.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mk.taskmaster.entities.Client;
import com.mk.taskmaster.entities.User;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
	List<Client> findByUserId(Long userId);
	
	 List<Client> findByUser(User user);
}
