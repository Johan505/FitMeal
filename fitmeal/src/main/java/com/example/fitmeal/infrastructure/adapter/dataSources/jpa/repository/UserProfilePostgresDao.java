package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository;

import com.example.fitmeal.domain.port.dao.UserProfileDao;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.UserProfileSpringJpaAdapterRepository;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserProfilePostgresDao implements UserProfileDao {

    private final UserProfileSpringJpaAdapterRepository userProfileRepository;

    public UserProfilePostgresDao(UserProfileSpringJpaAdapterRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void createUserProfile(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }

    @Override
    public Optional<UserProfile> findByUser_Id(String userId) {
        return userProfileRepository.findByUser_Id(userId);
    }
}
