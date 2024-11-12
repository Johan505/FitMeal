package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DietTypeRepository extends JpaRepository<DietType, Long> {
    Optional<DietType> findByName(String name);
}
