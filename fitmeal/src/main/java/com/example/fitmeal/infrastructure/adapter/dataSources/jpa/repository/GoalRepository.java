package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository;


import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    // Métodos adicionales personalizados según necesidades específicas, por ejemplo:
    Optional<Goal> findByName(String name);
}
