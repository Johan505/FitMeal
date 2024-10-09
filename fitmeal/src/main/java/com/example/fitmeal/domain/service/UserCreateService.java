package com.example.fitmeal.domain.service;


import com.example.fitmeal.domain.model.constants.UserConstants;
import com.example.fitmeal.domain.model.dto.command.UserCreateCommand;
import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.domain.model.exception.UserException;
import com.example.fitmeal.domain.port.dao.UserDao;
import com.example.fitmeal.domain.port.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCreateService {

    private final UserRepository userRepository;
    private final UserDao userDao;
    @Autowired
    public UserCreateService(UserRepository userRepository, UserDao userDao) {
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    public User execute(UserCreateCommand userCreateCommand) {

        User userByEmail = userDao.getByEmail(userCreateCommand.getEmail());

        if(userByEmail != null) {
            throw new UserException(String.format(UserConstants.USER_BY_EMAIL_ALREADY_EXISTS, userCreateCommand.getEmail()));
        }

        User userToCreate = new User()
                .requestToCreate(userCreateCommand);

        User savedUser = userRepository.create(userToCreate);

//        UserSearchable userSearchable = new UserSearchable(
//                savedUser.getId(),
//                savedUser.getEmail(),
//                savedUser.getFirstName(),
//                savedUser.getLastName(),
//                savedUser.getIsVerified(),
//                new HashSet<>()
//        );
//
//        userSearchableCreateService.execute(userSearchable);


        return savedUser;
    }

}
