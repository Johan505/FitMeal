package com.example.fitmeal.infrastructure.rest.controller;

import com.example.fitmeal.domain.model.dto.UserProfileDTO;
import com.example.fitmeal.domain.service.UserProfileService;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Goal;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final GoalRepository goalRepository; // Inyectar GoalRepository

    @Autowired
    public UserProfileController(UserProfileService userProfileService, GoalRepository goalRepository) {
        this.userProfileService = userProfileService;
        this.goalRepository = goalRepository; // Inicializar GoalRepository
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> validateAndSaveProfile(
            @PathVariable String userId,
            @RequestBody @Validated UserProfileDTO userProfileDTO) {

        // Buscar el Goal usando el goalId del DTO
        Goal goal = goalRepository.findById(userProfileDTO.getGoalId())
                .orElseThrow(() -> new IllegalArgumentException("Goal not found"));

        // Crear UserProfile y asignar el Goal
        UserProfile userProfile = UserProfile.builder()
                .weight(userProfileDTO.getWeight())
                .height(userProfileDTO.getHeight())
                .goal(goal)  // Asignación del Goal
                .sex(userProfileDTO.getSex())
                .age(userProfileDTO.getAge())
                .activityLevel(userProfileDTO.getActivityLevel())
                .build();

        String result = userProfileService.validateAndSaveProfile(userId, userProfile);

        if (result.startsWith("It's not recommended")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfileByUserId(@PathVariable String userId) {
        Optional<UserProfileDTO> userProfileDTO = userProfileService.getUserProfileByUserId(userId);
        return userProfileDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}