package com.simbancaire.simulateurpfa.payload.response;

import com.simbancaire.simulateurpfa.entites.Client;
import lombok.*;

@Builder
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class SimulationResponse {
    private Client client;
    private String id;
    private String typeDeCredit;
    private Double montantTotal;
    private Double montantTotalAvecInteret;
    private Double tauxInteret;
    private Integer duree;
    private Double mensualite;
    private Double fraisDeDossier;
}
