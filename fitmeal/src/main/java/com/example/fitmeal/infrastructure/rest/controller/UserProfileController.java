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
    public ResponseEntity<String> createUserProfile(
            @PathVariable String userId,
            @RequestBody @Validated UserProfileDTO userProfileDTO) {

        // Convertir el DTO a la entidad UserProfile
        UserProfile userProfile = UserProfile.builder()
                .weight(userProfileDTO.getWeight())
                .height(userProfileDTO.getHeight())
                .goal(userProfileDTO.getGoal())
                .sex(userProfileDTO.getSex())
                .caloriesNeeded(userProfileDTO.getCaloriesNeeded())
                .imc(userProfileDTO.getImc())
                .build();

        // Crear el perfil de usuario (usamos el UUID como string)
        userProfileService.createUserProfile(userId, userProfile);

        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil creado exitosamente.");
    }

}
