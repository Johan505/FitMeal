package com.example.fitmeal.infrastructure.security.service;

import com.example.fitmeal.domain.model.dto.UserDto;
import com.example.fitmeal.domain.model.exception.UserException;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.UserSpringJpaAdapterRepository;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
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
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Optional<UserEntity> user = userByEmailHandler.findByEmail(request.getEmail());
            if (user.isEmpty()) {
                throw new UserException("User not found");
            }

            UserProfile userProfile = user.get().getUserProfile();
            boolean profileComplete = isProfileComplete(userProfile); // Verificación del perfil
            String userid = user.get().getId();
            String firstname = user.get().getFirstName();
            String lastname = user.get().getLastName();

            String token = jwtUtilities.generateToken(user.get().getEmail());

            // Devolver el token y si el perfil está completo
            return new AuthenticationResponse(token, "Bearer", profileComplete, userid, firstname, lastname);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage().toLowerCase());
        }
    }


    // Método para verificar si el perfil está completo
    private boolean isProfileComplete(UserProfile userProfile) {
        return userProfile != null &&
                userProfile.getWeight() > 0 &&
                userProfile.getHeight() > 0 &&
                userProfile.getGoal() != null &&
                userProfile.getSex() != null &&
                userProfile.getCaloriesNeeded() > 0 &&
                userProfile.getImc() > 0 &&
                userProfile.getAge() > 0 &&
                userProfile.getActivityLevel() > 0;
    }

    @Override
    public UserEntity meByToken() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userByEmailHandler.findByEmail(userEmail).get();
    }
}