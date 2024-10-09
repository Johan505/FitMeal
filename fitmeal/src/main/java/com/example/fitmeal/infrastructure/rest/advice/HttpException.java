package com.example.fitmeal.infrastructure.rest.advice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HttpException {
    private String message;

    public static HttpException create(String message) {
        return new HttpException(message);
    }
}