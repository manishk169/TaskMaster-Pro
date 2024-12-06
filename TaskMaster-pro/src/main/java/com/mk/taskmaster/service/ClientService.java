package com.mk.taskmaster.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mk.taskmaster.entities.Client;
import com.mk.taskmaster.entities.User;
import com.mk.taskmaster.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    //add a new client
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    // Fetch a specific client by ID
    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
    }
    //get all clients details of current user
    public List<Client> getClientsByUser(User user) {
        return clientRepository.findByUserId(user.getId()); 
    }
}
