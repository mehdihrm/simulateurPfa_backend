package com.simbancaire.simulateurpfa.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimulationRequest {
    private Long idClient;
    private Double montantTotal;
    private Integer duree;
    private String typeDeCredit;
    private Double apport;
}
