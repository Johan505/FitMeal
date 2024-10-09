package com.example.fitmeal.infrastructure.rest.controller;


import com.example.fitmeal.application.query.UserByIdHandler;
import com.example.fitmeal.domain.model.dto.UserDto;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserQueryController {

    private final UserByIdHandler userByIdHandler;

    public UserQueryController(UserByIdHandler userByIdHandler) {
        this.userByIdHandler = userByIdHandler;
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable String id){
        return userByIdHandler.execute(id);
    }

}
