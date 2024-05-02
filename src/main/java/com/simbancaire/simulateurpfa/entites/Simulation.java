package com.simbancaire.simulateurpfa.entites;

import com.simbancaire.simulateurpfa.model.TypeCredit;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor @Data @Getter @Setter @NoArgsConstructor
@Entity
public class Simulation {
    @Id
    private String id;
    @ManyToOne
    private Client client;
    private Double montantCredit;
    private Integer dureeCredit;
    @Enumerated(value = EnumType.STRING)
    private TypeCredit typeDeCredit;
    // si crédit auto
    private Double apport;
}
