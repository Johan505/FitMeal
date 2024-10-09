package com.example.fitmeal.infrastructure.security.advice;


import com.example.fitmeal.infrastructure.rest.advice.HttpException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;

@ControllerAdvice
public class AuthenticationControllerAdvice {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HttpException> handleException(AuthenticationException exception) {
        return new ResponseEntity<>(HttpException.create(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpException> handleException(BadCredentialsException exception) {
        return new ResponseEntity<>(HttpException.create(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpException> handleException(UsernameNotFoundException exception) {
        return new ResponseEntity<>(HttpException.create(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpException> handleException(BadRequestException exception) {
        return new ResponseEntity<>(HttpException.create(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
