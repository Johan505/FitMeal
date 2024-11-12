package com.example.fitmeal.infrastructure.security.rest;


import com.example.fitmeal.infrastructure.security.JwtUtilities;
import com.example.fitmeal.infrastructure.security.request.AuthenticationRequest;
import com.example.fitmeal.infrastructure.security.response.AuthenticationResponse;
import com.example.fitmeal.infrastructure.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthenticateCommandController {

    private final AuthenticationService authenticationService;
    private final JwtUtilities jwtUtilities;

    @Autowired
    public AuthenticateCommandController(AuthenticationService authenticationService, JwtUtilities jwtUtilities) {
        this.authenticationService = authenticationService;
        this.jwtUtilities = jwtUtilities;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/isValid")
    public ResponseEntity<Boolean> isValid(HttpServletRequest request) {
        String token = jwtUtilities.getToken(request);
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean isValid = jwtUtilities.validateToken(token);
        if (isValid) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().build();
    }
}
