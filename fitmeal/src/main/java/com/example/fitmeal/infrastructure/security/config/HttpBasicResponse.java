package com.example.fitmeal.infrastructure.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HttpBasicResponse {
    public String message;
    public HttpStatus code;
}