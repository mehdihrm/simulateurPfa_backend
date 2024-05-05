package com.simbancaire.simulateurpfa.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class AuthenticationResponse {
    private String username;
    private String token;

}
