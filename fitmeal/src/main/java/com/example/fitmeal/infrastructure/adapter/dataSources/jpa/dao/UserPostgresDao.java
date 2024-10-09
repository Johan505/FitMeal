package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.dao;


import com.example.fitmeal.domain.model.constants.UserConstants;
import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.domain.model.exception.UserException;
import com.example.fitmeal.domain.port.dao.UserDao;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.UserSpringJpaAdapterRepository;
import com.example.fitmeal.infrastructure.adapter.mapper.UserDboMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPostgresDao implements UserDao {

    private final UserSpringJpaAdapterRepository userSpringJpaAdapterRepository;
    private final UserDboMapper userDboMapper;

    public UserPostgresDao(UserSpringJpaAdapterRepository userSpringJpaAdapterRepository, UserDboMapper userDboMapper) {
        this.userSpringJpaAdapterRepository = userSpringJpaAdapterRepository;
        this.userDboMapper = userDboMapper;
    }

    @Override
    public User getById(String id) {
        var optionalUser = userSpringJpaAdapterRepository.findById(id);

        if (optionalUser.isEmpty()){
            throw new UserException(
                    String.format(UserConstants.USER_BY_ID_NOT_FOUND, id));
        }

        return userDboMapper.toDomain(optionalUser.get());
    }

    @Override
    public User getByEmail(String email) {
        var optionalUser = userSpringJpaAdapterRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            return null;
        }

        return userDboMapper.toDomain(optionalUser.get());
    }

    @Override
    public List<User> getUsersIfContainsEmailOrFirstNameOrLastName(String keyWord) {
        return userSpringJpaAdapterRepository.searchUsersByKeyword(keyWord).stream().map(userDboMapper::toDomain).toList();
    }
}
