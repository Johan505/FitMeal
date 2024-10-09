package com.example.fitmeal.application.query;

import com.example.fitmeal.application.mapper.UserDtoMapper;
import com.example.fitmeal.domain.model.dto.UserDto;
import com.example.fitmeal.domain.service.UserByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserByIdHandler {

    private final UserByIdService userByIdService;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserByIdHandler(UserByIdService userByIdService, UserDtoMapper userDtoMapper) {
        this.userByIdService = userByIdService;
        this.userDtoMapper = userDtoMapper;
    }

    public UserDto execute(String id){
        return userDtoMapper
                .toDto(userByIdService.execute(id));
    }

}