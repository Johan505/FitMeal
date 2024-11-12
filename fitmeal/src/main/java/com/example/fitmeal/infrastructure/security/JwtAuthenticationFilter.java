package com.example.fitmeal.infrastructure.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  final JwtUtilities jwtUtilities ;
    private final CustomerUserDetailsService customerUserDetailsService ;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            log.debug("Starting JWT authentication filter");
            String token = jwtUtilities.getToken(request);
            log.debug("Token retrieved: {}", token);

            if (token != null && jwtUtilities.validateToken(token)) {
                String email = jwtUtilities.extractUsername(token);
                log.debug("Extracted username from token: {}", email);

                UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    log.info("Authenticated user with email: {}", email);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                log.warn("Token is null or invalid");
            }
        } catch (ExpiredJwtException e) {
            log.warn("Token has expired");
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            log.error("Error processing JWT authentication", e);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }


}
