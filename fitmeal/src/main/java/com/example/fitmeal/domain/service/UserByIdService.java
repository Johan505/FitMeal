package com.example.fitmeal.domain.service;


import com.example.fitmeal.domain.model.constants.UserConstants;
import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.domain.model.exception.UserException;
import com.example.fitmeal.domain.port.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class UserByIdService {
    private final UserDao userDao;

    @Autowired
    public UserByIdService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User execute(String id) {
        User user = userDao.getById(id);
        if (user == null) {
            throw new UserException(String.format(UserConstants.USER_BY_ID_NOT_FOUND, id));
        }
        return user;
    }
}
