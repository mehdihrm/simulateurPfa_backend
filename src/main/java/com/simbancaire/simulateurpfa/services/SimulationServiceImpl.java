package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.Simulation;
import com.simbancaire.simulateurpfa.model.Function;
import com.simbancaire.simulateurpfa.payload.response.SimulationResponse;
import com.simbancaire.simulateurpfa.model.TypeCredit;
import com.simbancaire.simulateurpfa.repositories.SimulationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Service
public class SimulationServiceImpl implements SimulationService {

    private static final double FRAIS_DOSSIER_FIXES = 100.0;
    private static final double POURCENTAGE_FRAIS_DOSSIER_FIXES = 0.01;
    private SimulationRepository simulationRepository;
    @Override
    public SimulationResponse getSimulation(Simulation simulation) {

        return SimulationResponse.builder()
                .duree(simulation.getDureeCredit())
                .fraisDeDossier(calculerFraisDossier(simulation))
                .mensulaite(calculerMensualite(simulation))
                .montantTotal(simulation.getMontantCredit())
                .montantTotalAvecInteret(calculerMontantTotalRembourse(simulation))
                .client(simulation.getClient())
                .tauxInteret(getTauxInteret(simulation))
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
    public Simulation saveSimulation(Simulation simulation) {
        return simulationRepository.save(simulation);
    }
}
