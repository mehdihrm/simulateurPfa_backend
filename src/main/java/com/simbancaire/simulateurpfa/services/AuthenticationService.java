package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.User;
import com.simbancaire.simulateurpfa.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(User request);
    AuthenticationResponse authenticate(User request);
}
