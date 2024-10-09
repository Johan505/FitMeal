package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository;


import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.domain.port.repository.UserRepository;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.UserSpringJpaAdapterRepository;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.adapter.mapper.UserDboMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional
public class UserPostgresRepository implements UserRepository {

    private final UserSpringJpaAdapterRepository userSpringJpaAdapterRepository;
    private final UserDboMapper userDboMapper;

    public UserPostgresRepository(UserSpringJpaAdapterRepository userSpringJpaAdapterRepository, UserDboMapper userDboMapper) {
        this.userSpringJpaAdapterRepository = userSpringJpaAdapterRepository;
        this.userDboMapper = userDboMapper;
    }

    @Override
    @Transactional
    public User create(User user) {
        var userToSave = userDboMapper.toDboCreate(user);
        UserEntity userSaved = userSpringJpaAdapterRepository.save(userToSave);
        return userDboMapper.toDomain(userSaved);
    }

    @Override
    @Transactional
    public void updateVerified(User user) {
        var userToSave = userDboMapper.toDbo(user);
        userSpringJpaAdapterRepository.save(userToSave);
    }

}
