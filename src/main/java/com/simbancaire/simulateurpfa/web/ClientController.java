package com.simbancaire.simulateurpfa.web;

import com.simbancaire.simulateurpfa.entites.Client;
import com.simbancaire.simulateurpfa.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody Client request){
        return ResponseEntity.ok(clientService.addClient(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client request){
        return ResponseEntity.ok(clientService.updateClient(request));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id){
       clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id){
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Client>> getAllClient(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

}
