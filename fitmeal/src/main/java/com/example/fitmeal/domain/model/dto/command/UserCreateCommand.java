package com.example.fitmeal.domain.model.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateCommand {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
