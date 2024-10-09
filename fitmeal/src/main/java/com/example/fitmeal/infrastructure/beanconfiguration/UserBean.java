package com.example.fitmeal.infrastructure.beanconfiguration;


import com.example.fitmeal.domain.port.dao.UserDao;
import com.example.fitmeal.domain.port.repository.UserRepository;
import com.example.fitmeal.domain.service.UserByIdService;
import com.example.fitmeal.domain.service.UserCreateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBean {
    @Bean
    public UserByIdService userByIdService(UserDao userDao){
        return new UserByIdService(userDao);
    }



    @Bean
    public UserCreateService userCreateService(
            UserRepository userRepository,
            UserDao userDao
    ){
        return new UserCreateService(userRepository, userDao);
    }

}
