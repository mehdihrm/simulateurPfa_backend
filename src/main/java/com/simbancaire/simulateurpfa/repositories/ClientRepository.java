package com.simbancaire.simulateurpfa.repositories;

import com.simbancaire.simulateurpfa.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client getClientById(Long id);

    void deleteClientById(Long id);
}
