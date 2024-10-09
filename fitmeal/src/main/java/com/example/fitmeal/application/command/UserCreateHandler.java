package com.example.fitmeal.application.command;


import com.example.fitmeal.application.mapper.UserDtoMapper;
import com.example.fitmeal.domain.model.dto.UserDto;
import com.example.fitmeal.domain.model.dto.command.UserCreateCommand;
import com.example.fitmeal.domain.service.UserCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreateHandler {

    private final UserCreateService userCreateService;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserCreateHandler(UserCreateService userCreateService, UserDtoMapper userDtoMapper) {
        this.userCreateService = userCreateService;
        this.userDtoMapper = userDtoMapper;
    }

    public UserDto execute(UserCreateCommand userCreateCommand) {
        return userDtoMapper
                .toDto(userCreateService.execute(userCreateCommand));
    }

}
