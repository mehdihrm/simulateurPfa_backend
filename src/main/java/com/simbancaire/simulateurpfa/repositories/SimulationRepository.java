package com.simbancaire.simulateurpfa.repositories;

import com.simbancaire.simulateurpfa.entites.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationRepository extends JpaRepository<Simulation,String> {
}
