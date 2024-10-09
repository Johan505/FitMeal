package com.example.fitmeal.infrastructure.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws io.jsonwebtoken.io.IOException, ServletException, java.io.IOException {
        Throwable cause = authException.getCause();
        if(cause != null) {
            if (cause instanceof ExpiredJwtException) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(
                        new ObjectMapper().writeValueAsString(
                                new HttpBasicResponse("Token expired The token has expired, please log in again.", HttpStatus.NOT_ACCEPTABLE)
                        )
                );
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(
                        new ObjectMapper().writeValueAsString(
                                new HttpBasicResponse("Unauthorized: " +cause.getCause().getCause().getMessage(), HttpStatus.BAD_REQUEST)
                        )
                );
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            new HttpBasicResponse("Unauthorized: " +authException.getMessage(), HttpStatus.BAD_REQUEST)
                    )
            );
        }

    }
}
