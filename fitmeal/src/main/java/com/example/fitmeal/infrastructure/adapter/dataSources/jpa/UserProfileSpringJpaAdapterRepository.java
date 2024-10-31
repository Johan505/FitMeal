package com.example.fitmeal.infrastructure.adapter.dataSources.jpa;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileSpringJpaAdapterRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser_Id(String userId);
}
