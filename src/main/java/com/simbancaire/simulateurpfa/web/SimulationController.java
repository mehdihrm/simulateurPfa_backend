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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/simulation")
public class SimulationController {
    private final SimulationService simulationService;
    private final ClientService clientService;
    @PostMapping("/new")
    public ResponseEntity<SimulationResponse> simulerCredit(@RequestBody SimulationRequest request){
        Simulation simulation = new Simulation();
        try{
            Client client = clientService.getClientById(request.getIdClient());
            simulation.setTypeDeCredit(TypeCredit.valueOf(request.getTypeDeCredit()));
            simulation.setMontantCredit(request.getMontantTotal());
            simulation.setDureeCredit(request.getDuree());
            simulation.setApport(request.getApport());
            simulation.setClient(client);

        }catch (Exception e){
            System.out.println("Erreur : "+e.getMessage());
        }
        return ResponseEntity.ok(simulationService.getSimulation(simulation));
    }
}
