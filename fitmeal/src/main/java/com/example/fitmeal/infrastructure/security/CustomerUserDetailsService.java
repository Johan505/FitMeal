package com.example.fitmeal.infrastructure.security;


import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.UserSpringJpaAdapterRepository;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserSpringJpaAdapterRepository userSpringJpaAdapterRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userSpringJpaAdapterRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }


}
