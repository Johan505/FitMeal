package com.example.fitmeal.domain.port.dao;


import com.example.fitmeal.domain.model.entity.User;

import java.util.List;

public interface UserDao {
    User getById(String id);
    User getByEmail(String email);
    List<User> getUsersIfContainsEmailOrFirstNameOrLastName(String searchString);
}
