package com.example.fitmeal.domain.model.exception;


import java.io.Serial;

public class UserException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2L;

    private String errorMessage;

    public UserException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public UserException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
