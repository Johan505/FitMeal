package com.example.fitmeal.domain.port.dao;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;

import java.util.Optional;

public interface UserProfileDao {
    void createUserProfile(UserProfile userProfile);
    Optional<UserProfile> findByUser_Id(String userId);
}
