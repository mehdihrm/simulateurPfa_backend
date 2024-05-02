package com.simbancaire.simulateurpfa.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbancaire.simulateurpfa.model.Function;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Enumerated(value = EnumType.STRING)
    private Function profession;
    private String email;
    private String phoneNumber;
    @OneToMany(mappedBy="client",fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Simulation> simulations;
}
