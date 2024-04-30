package com.simbancaire.simulateurpfa.web;

import com.simbancaire.simulateurpfa.entites.User;
import com.simbancaire.simulateurpfa.model.AuthenticationResponse;
import com.simbancaire.simulateurpfa.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController @AllArgsConstructor
public class AuthenticationController {
        private final AuthenticationService authenticationService;

        @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
            return ResponseEntity.ok(authenticationService.register(request));
        }
        @PostMapping("/login")
        public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
            return ResponseEntity.ok(authenticationService.authenticate(request));
        }
}
