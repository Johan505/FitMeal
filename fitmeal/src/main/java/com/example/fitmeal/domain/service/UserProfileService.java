package com.example.fitmeal.domain.service;

import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.fitmeal.domain.port.dao.UserProfileDao;
import com.example.fitmeal.domain.port.dao.UserDao;
import com.example.fitmeal.infrastructure.adapter.mapper.UserDboMapper;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileDao userProfileDao;
    private final UserDao userDao;
    private final UserDboMapper userDboMapper; // Inyección del mapper

    @Autowired
    public UserProfileService(UserProfileDao userProfileDao, UserDao userDao, UserDboMapper userDboMapper) {
        this.userProfileDao = userProfileDao;
        this.userDao = userDao;
        this.userDboMapper = userDboMapper;
    }

    public void createUserProfile(String userId, UserProfile userProfile) {
        // Obtener el User del dominio
        User user = userDao.getById(userId);

        // Convertir User a UserEntity
        UserEntity userEntity = userDboMapper.toDbo(user);

        // Asociar el perfil con el UserEntity
        userProfile.setUser(userEntity);

        // Guardar el perfil
        userProfileDao.createUserProfile(userProfile);
    }
}
