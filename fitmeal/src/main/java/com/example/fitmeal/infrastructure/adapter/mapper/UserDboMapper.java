package com.example.fitmeal.infrastructure.adapter.mapper;

import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;


@Component
public class UserDboMapper {

    public UserEntity toDbo(User domain){
        if(domain == null){
            return null;
        }
        return new UserEntity(domain.getId(), domain.getFirstName(), domain.getLastName(), domain.getEmail(), domain.getPassword(), domain.getIsVerified());
    }

    public UserEntity toDboCreate(User domain){
        if(domain == null){
            return null;
        }
        return new UserEntity(domain.getId(), domain.getFirstName(), domain.getLastName(), domain.getEmail(), domain.getPassword(), domain.getIsVerified());
    }

    public User toDomain(UserEntity entity){
        if(entity == null){
            return null;
        }
        return new User(entity.getId(), entity.getEmail(), entity.getFirstName(), entity.getLastName(), entity.getPassword(), entity.isVerified());
    }

}
