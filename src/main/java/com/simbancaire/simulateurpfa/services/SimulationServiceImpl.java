package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.Client;
import com.simbancaire.simulateurpfa.entites.Simulation;
import com.simbancaire.simulateurpfa.model.Function;
import com.simbancaire.simulateurpfa.payload.request.SimulationRequest;
import com.simbancaire.simulateurpfa.payload.response.SimulationResponse;
import com.simbancaire.simulateurpfa.model.TypeCredit;
import com.simbancaire.simulateurpfa.repositories.ClientRepository;
import com.simbancaire.simulateurpfa.repositories.SimulationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class SimulationServiceImpl implements SimulationService {

    private static final double FRAIS_DOSSIER_FIXES = 100.0;
    private static final double POURCENTAGE_FRAIS_DOSSIER_FIXES = 0.01;

    private final SimulationRepository simulationRepository;
    private final ClientRepository clientRepository;


    @Override
    public SimulationResponse getSimulation(Simulation simulation) {
        double fraisDeDossier = calculerFraisDossier(simulation);
        double mensualite = calculerMensualite(simulation);
        double montantTotalAvecInteret = calculerMontantTotalRembourse(simulation);
        double montantTotal;
        if(simulation.getTypeDeCredit() == TypeCredit.AUTOMOBILE){
            montantTotal = simulation.getMontantCredit() - simulation.getApport();
        }else{
            montantTotal = simulation.getMontantCredit();
        }

        BigDecimal mensualiteArrondie = BigDecimal.valueOf(mensualite).setScale(2, RoundingMode.HALF_UP);
        BigDecimal montantTotalAvecInteretArrondi = BigDecimal.valueOf(montantTotalAvecInteret).setScale(2, RoundingMode.HALF_UP);

        return SimulationResponse.builder()
                .duree(simulation.getDureeCredit())
                .fraisDeDossier(fraisDeDossier)
                .mensualite(mensualiteArrondie.doubleValue())
                .montantTotal(montantTotal)
                .montantTotalAvecInteret(montantTotalAvecInteretArrondi.doubleValue())
                .client(simulation.getClient())
                .tauxInteret(getTauxInteret(simulation) * 100)
                .id(simulation.getId())
                .typeDeCredit(String.valueOf(simulation.getTypeDeCredit()))
                .build();
    }



    @Override
    public Double getTauxInteret(Simulation simulation) {
        return switch (simulation.getTypeDeCredit()) {
            case AUTOMOBILE -> getTauxInteretAutomobile(simulation.getClient().getProfession());
            case CONSOMATION -> getTauxInteretConsomation(simulation.getClient().getProfession());
            case IMMOBILIER -> getTauxInteretImmobilier(simulation.getClient().getProfession());
        };
    }

    @Override
    public Double getTauxInteretAutomobile(Function profession) {
        return switch (profession) {
            case SALARIE -> 0.03;
            case RETRAITE, AUTRE -> 0.04;
            case FONCTIONNAIRE -> 0.02;
        };
    }

    @Override
    public Double getTauxInteretConsomation(Function profession) {
        return switch (profession) {
            case SALARIE -> 0.06;
            case RETRAITE, AUTRE -> 0.07;
            case FONCTIONNAIRE -> 0.05;
        };
    }

    @Override
    public Double getTauxInteretImmobilier(Function profession) {
        return switch (profession) {
            case SALARIE -> 0.03;
            case RETRAITE, AUTRE -> 0.04;
            case FONCTIONNAIRE -> 0.02;
        };
    }

    @Override
    public Double calculerMensualite(Simulation simulation) {
        Double tauxInteret = getTauxInteret(simulation) / 12;
        int dureeCredit = simulation.getDureeCredit();
        Double montantCredit = simulation.getMontantCredit();
        if(simulation.getTypeDeCredit() == TypeCredit.AUTOMOBILE){
            montantCredit -= simulation.getApport();
        }

        return (montantCredit * tauxInteret) / (1 - Math.pow(1 + tauxInteret, -dureeCredit));
    }

    @Override
    public Double calculerMontantTotalRembourse(Simulation simulation) {

        return calculerMensualite(simulation) * simulation.getDureeCredit();
    }

    @Override
    public Double calculerFraisDossier(Simulation simulation) {
        return (simulation.getMontantCredit() * POURCENTAGE_FRAIS_DOSSIER_FIXES) + FRAIS_DOSSIER_FIXES;
    }

    @Override
    public void saveSimulation(Simulation simulation) {
        this.simulationRepository.save(simulation);
    }

    @Override
    public Simulation updateSimulation(SimulationRequest request) {
        Simulation simulation = simulationRepository.findById(request.getId()).orElseThrow();
        Client client = clientRepository.findById(request.getIdClient()).orElseThrow();
        simulation.setClient(client);
        simulation.setApport(request.getApport());
        simulation.setDureeCredit(request.getDuree());
        simulation.setMontantCredit(request.getMontantTotal());
        simulation.setTypeDeCredit(TypeCredit.valueOf(request.getTypeDeCredit()));
        return simulationRepository.save(simulation);


    }

    @Override
    public void deleteSimulation(String id) {
        simulationRepository.deleteById(id);
    }

    @Override
    public List<Simulation> getAll() {
        return simulationRepository.findAll();
    }
}
