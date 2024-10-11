package com.example.fitmeal.infrastructure.rest.controller;

import com.example.fitmeal.domain.model.dto.UserProfileDTO;
import com.example.fitmeal.domain.service.UserProfileService;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> validateAndSaveProfile(
            @PathVariable String userId,
            @RequestBody @Validated UserProfileDTO userProfileDTO) {

        // Convert DTO to UserProfile entity (without setting imc and caloriesNeeded)
        UserProfile userProfile = UserProfile.builder()
                .weight(userProfileDTO.getWeight())
                .height(userProfileDTO.getHeight())
                .goal(userProfileDTO.getGoal())
                .sex(userProfileDTO.getSex())
                .build();

        // Validate and save profile
        String result = userProfileService.validateAndSaveProfile(userId, userProfile);

        // Check if profile was saved or if there was a warning
        if (result.startsWith("It's not recommended")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


}