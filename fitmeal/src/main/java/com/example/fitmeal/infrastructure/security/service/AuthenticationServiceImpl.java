package com.example.fitmeal.infrastructure.security.service;


import com.example.fitmeal.domain.model.dto.UserDto;
import com.example.fitmeal.domain.model.exception.UserException;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.UserSpringJpaAdapterRepository;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.security.JwtUtilities;
import com.example.fitmeal.infrastructure.security.request.AuthenticationRequest;
import com.example.fitmeal.infrastructure.security.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;
    private final UserSpringJpaAdapterRepository userByEmailHandler;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtUtilities jwtUtilities, UserSpringJpaAdapterRepository userByEmailHandler) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilities = jwtUtilities;
        this.userByEmailHandler = userByEmailHandler;
    }

    @Override
    public AuthenticationResponse signIn(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<UserEntity> user = userByEmailHandler.findByEmail(request.getEmail());
            if (user == null) {
                throw new UserException("User not found");
            }
            return new AuthenticationResponse(jwtUtilities.generateToken(user.get().getEmail()), "Bearer");
        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage().toLowerCase());
        }
    }

    @Override
    public UserEntity meByToken() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userByEmailHandler.findByEmail(userEmail).get();
    }
}
