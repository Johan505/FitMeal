package com.example.fitmeal.infrastructure.security.service;


import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.security.request.AuthenticationRequest;
import com.example.fitmeal.infrastructure.security.response.AuthenticationResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationService {
    AuthenticationResponse signIn(AuthenticationRequest request);
    UserEntity meByToken();
}
