package com.example.fitmeal.infrastructure.utils;


import com.example.fitmeal.domain.model.exception.UserException;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static String getAuthenticatedUserSecurityContext() {
        String ownerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (ownerEmail == null) {
            throw new UserException("Unauthorized");
        }
        return ownerEmail;
    }
}
