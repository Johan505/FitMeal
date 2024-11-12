package com.example.fitmeal.infrastructure.security.rest;


import com.example.fitmeal.domain.model.dto.UserDto;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.security.JwtUtilities;
import com.example.fitmeal.infrastructure.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthenticateQueryController {
    private final AuthenticationService authenticationService;
    private final JwtUtilities jwtUtilities;

    @Autowired
    public AuthenticateQueryController(AuthenticationService authenticationService, JwtUtilities jwtUtilities) {
        this.authenticationService = authenticationService;
        this.jwtUtilities = jwtUtilities;
    }


    @GetMapping("/me")
    public ResponseEntity<UserEntity> getMe(HttpServletRequest request) {
        String token = jwtUtilities.getToken(request);
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authenticationService.meByToken());
    }
}
