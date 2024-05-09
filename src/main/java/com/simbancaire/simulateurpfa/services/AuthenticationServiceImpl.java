package com.simbancaire.simulateurpfa.services;

import com.simbancaire.simulateurpfa.entites.User;
import com.simbancaire.simulateurpfa.payload.response.AuthenticationResponse;
import com.simbancaire.simulateurpfa.model.Role;
import com.simbancaire.simulateurpfa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;

@Service
@AllArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(user.getUsername(),token,user.getRole().toString());
    }

    public AuthenticationResponse authenticate(User request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            User user = userRepository.findByUserName(request.getUsername());
            String token = jwtService.generateToken(user);
            return new AuthenticationResponse(user.getUsername(), token,user.getRole().toString());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Nom d'utilisateur ou mot de passe incorrect");
        }
    }
}
