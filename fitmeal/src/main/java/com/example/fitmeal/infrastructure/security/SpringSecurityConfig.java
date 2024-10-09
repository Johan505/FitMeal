package com.example.fitmeal.infrastructure.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/api-docs").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/swagger-ui.html").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/swagger-ui/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/swagger-resources/**").permitAll()
                                        .requestMatchers("/swagger-ui.html").permitAll()
                                        .requestMatchers("/swagger-ui/**").permitAll()
                                        .requestMatchers("/api-docs").permitAll()
                                        .requestMatchers("/api-docs/**").permitAll()
                                        // authorize all requests for the websocket
                                        .requestMatchers("/api/ws").permitAll()
                                        .requestMatchers("/api/ws-message").permitAll()
                                        .requestMatchers("/api/ws/**").permitAll()
                                        .requestMatchers("/api/ws-message/**").permitAll()
                                        .requestMatchers("/ws").permitAll()
                                        .requestMatchers("/ws-message").permitAll()
                                        .requestMatchers("/ws/**").permitAll()
                                        .requestMatchers("/ws-message/**").permitAll()
                                        .requestMatchers("/topic/**").permitAll()
                                        .requestMatchers("/app/**").permitAll()
                                        .requestMatchers("/api/ws/info").permitAll()
                                        .requestMatchers("/ws/info").permitAll()
                                        .requestMatchers("/app/ws").permitAll()

                                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
