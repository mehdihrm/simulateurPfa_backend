package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.Simulation;
import com.simbancaire.simulateurpfa.model.Function;
import com.simbancaire.simulateurpfa.payload.request.SimulationRequest;
import com.simbancaire.simulateurpfa.payload.response.SimulationResponse;

import java.util.List;

public interface SimulationService {
    SimulationResponse getSimulation(Simulation simulation);
    Double getTauxInteret(Simulation simulation);
    Double getTauxInteretAutomobile(Function profession);
    Double getTauxInteretConsomation(Function profession);
    Double getTauxInteretImmobilier(Function profession);
    Double calculerMensualite(Simulation simulation);
    Double calculerMontantTotalRembourse(Simulation simulation);
    Double calculerFraisDossier(Simulation simulation);
    void saveSimulation(Simulation simulation);
    Simulation updateSimulation(SimulationRequest simulation);
    void deleteSimulation(String id);
    List<Simulation> getAll();
}
