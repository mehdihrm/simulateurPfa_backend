package com.simbancaire.simulateurpfa.web;

import com.simbancaire.simulateurpfa.entites.Client;
import com.simbancaire.simulateurpfa.entites.Simulation;
import com.simbancaire.simulateurpfa.model.TypeCredit;
import com.simbancaire.simulateurpfa.payload.request.SimulationRequest;
import com.simbancaire.simulateurpfa.payload.response.SimulationResponse;
import com.simbancaire.simulateurpfa.services.ClientService;
import com.simbancaire.simulateurpfa.services.SimulationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/simulation")
public class SimulationController {
    private final SimulationService simulationService;
    private final ClientService clientService;
    @PostMapping("/new")
    public ResponseEntity<SimulationResponse> newSimulationCredit(@RequestBody SimulationRequest request){
        Simulation simulation = new Simulation();
        try{
            Client client = clientService.getClientById(request.getIdClient());
            simulation.setId(UUID.randomUUID().toString());
            simulation.setTypeDeCredit(TypeCredit.valueOf(request.getTypeDeCredit()));
            simulation.setMontantCredit(request.getMontantTotal());
            simulation.setDureeCredit(request.getDuree());
            simulation.setApport(request.getApport());
            simulation.setClient(client);
            simulationService.saveSimulation(simulation);

        }catch (Exception e){
            System.out.println("Erreur : "+e.getMessage());
        }
        return ResponseEntity.ok(simulationService.getSimulation(simulation));
    }
    @PostMapping("/simuler")
    public ResponseEntity<SimulationResponse> simulerCredit(@RequestBody Simulation request){
        return ResponseEntity.ok(simulationService.getSimulation(request));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Simulation>> getAllSimulations(){
        return ResponseEntity.ok(simulationService.getAll());
    }
}
