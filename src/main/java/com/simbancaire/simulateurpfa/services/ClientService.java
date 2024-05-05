package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.Client;

import java.util.List;

public interface ClientService {
    Client addClient(Client client);
    Client updateClient(Client client);
    void deleteClient(Long id);
    Client getClientById(Long id);
    List<Client> getAllClients();
}
