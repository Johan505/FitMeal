package com.example.fitmeal.infrastructure.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String type;
    private boolean profileComplete;
    private String userid;
    private String firstname;
    private String lastname;
}