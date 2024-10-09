package com.example.fitmeal.infrastructure.rest.advice;


import com.example.fitmeal.domain.model.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<HttpException> handleEmptyInput(UserException emptyInputException){
        return new ResponseEntity<>(HttpException.create(emptyInputException.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }
}
