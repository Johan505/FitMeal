package com.example.fitmeal.domain.port.dao;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;

public interface UserProfileDao {
    void createUserProfile(UserProfile userProfile);
}