package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.Client;

public interface ClientService {
    Client addClient(Client client);
    Client updateClient(Client client);
    void deleteClient(Long id);
    Client getClientById(Long id);
}
