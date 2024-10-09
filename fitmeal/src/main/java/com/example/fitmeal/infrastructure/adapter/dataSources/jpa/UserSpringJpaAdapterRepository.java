package com.example.fitmeal.infrastructure.adapter.dataSources.jpa;


import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserSpringJpaAdapterRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.email LIKE %:keyword% OR u.firstName LIKE %:keyword% OR u.lastName LIKE %:keyword%")
    List<UserEntity> searchUsersByKeyword(@Param("keyword") String keyword);
}
