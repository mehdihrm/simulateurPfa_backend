package com.simbancaire.simulateurpfa.web;

import com.simbancaire.simulateurpfa.entites.User;
import com.simbancaire.simulateurpfa.payload.response.AuthenticationResponse;
import com.simbancaire.simulateurpfa.payload.response.MessageResponse;
import com.simbancaire.simulateurpfa.repositories.UserRepository;
import com.simbancaire.simulateurpfa.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/auth")
@RestController @AllArgsConstructor
public class AuthenticationController {
        private final AuthenticationService authenticationService;
        private final UserRepository userRepository;

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody User request){
            if(userRepository.existsByEmail(request.getEmail())){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
            }
            if(userRepository.existsByUserName(request.getUsername())){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }
            return ResponseEntity.ok(authenticationService.register(request));
        }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        User user = userRepository.findByUserName(authenticationResponse.getUsername());
        if(user.getEnabled()){
            return ResponseEntity.ok().body(authenticationResponse);
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Account inactivated contact your admin!"));
        }

    }
}
