package com.example.fitmeal.domain.model.entity;


import com.example.fitmeal.domain.model.dto.command.UserCreateCommand;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Setter
public class User {

    private UserId id;
    private UserEmail email;
    private UserFirstName firstName;
    private UserLastName lastName;
    private UserPassword password;
    private boolean isVerified;

    public User(String id, String email, String firstName, String lastName, String password, boolean isVerified) {
        this.id = new UserId(id);
        this.email = new UserEmail(email);
        this.firstName = new UserFirstName(firstName);
        this.lastName = new UserLastName(lastName);
        this.password = new UserPassword(password);
        this.isVerified = isVerified;
    }

    public User(String email, String firstName, String lastName, String password, boolean isVerified) {
        this.id = new UserId(UUID.randomUUID().toString());
        this.email = new UserEmail(email);
        this.firstName = new UserFirstName(firstName);
        this.lastName = new UserLastName(lastName);
        this.password = new UserPassword(password);
        this.isVerified = isVerified;
    }

    public User requestToCreate(UserCreateCommand userCreateCommand){
        this.id = new UserId(UUID.randomUUID().toString());
        this.email = new UserEmail(userCreateCommand.getEmail());
        this.firstName = new UserFirstName(userCreateCommand.getFirstName());
        this.lastName = new UserLastName(userCreateCommand.getLastName());
        this.password = new UserPassword(userCreateCommand.getPassword());
        this.isVerified = false;
        return this;
    }


    public String getId() {
        return this.id.getId();
    }

    public String getEmail() {
        return this.email.getEmail();
    }

    public String getFirstName() {
        return this.firstName.getFirstName();
    }

    public String getLastName() {
        return this.lastName.getLastName();
    }

    public String getPassword() {
        return this.password.getPassword();
    }

    public boolean getIsVerified() {
        return this.isVerified;
    }
}
