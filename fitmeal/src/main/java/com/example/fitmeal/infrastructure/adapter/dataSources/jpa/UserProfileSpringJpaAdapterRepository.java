package com.example.fitmeal.infrastructure.adapter.dataSources.jpa;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileSpringJpaAdapterRepository extends JpaRepository<UserProfile, Long> {
    // Consultas personalizadas si son necesarias
}
