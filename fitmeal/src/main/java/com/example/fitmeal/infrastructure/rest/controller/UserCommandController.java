package com.example.fitmeal.infrastructure.rest.controller;


import com.example.fitmeal.application.command.UserCreateHandler;
import com.example.fitmeal.domain.model.dto.UserDto;
import com.example.fitmeal.domain.model.dto.command.UserCreateCommand;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserCommandController {

    private final UserCreateHandler userCreateHandler;
    private final PasswordEncoder passwordEncoder;

    public UserCommandController(UserCreateHandler userCreateHandler, PasswordEncoder passwordEncoder) {
        this.userCreateHandler = userCreateHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping()
    public UserDto create(@RequestBody UserCreateCommand userCreateCommand) {
        userCreateCommand.setPassword(passwordEncoder.encode(userCreateCommand.getPassword()));
        return userCreateHandler.execute(userCreateCommand);
    }

}
