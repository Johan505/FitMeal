package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserDietPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDietPreferenceRepository extends JpaRepository<UserDietPreference, Long> {
    boolean existsByUserId(String userId);
}
