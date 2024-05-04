package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.Client;
import com.simbancaire.simulateurpfa.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        Client originalClient = clientRepository.getClientById(client.getId());
        try{
            originalClient.setLastName(client.getLastName());
            originalClient.setFirstName(client.getFirstName());
            originalClient.setEmail(client.getEmail());
            originalClient.setProfession(client.getProfession());
            originalClient.setPhoneNumber(client.getPhoneNumber());
            originalClient.setDateOfBirth(client.getDateOfBirth());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return clientRepository.save(originalClient);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteClientById(id);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.getClientById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
