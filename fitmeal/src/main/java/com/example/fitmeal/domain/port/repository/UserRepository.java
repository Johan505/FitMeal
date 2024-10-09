package com.example.fitmeal.domain.port.repository;


import com.example.fitmeal.domain.model.entity.User;

public interface UserRepository {
    User create(User user);
    void updateVerified(User user);
}
