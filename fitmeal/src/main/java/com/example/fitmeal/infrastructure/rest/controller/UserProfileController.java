package com.example.fitmeal.infrastructure.rest.controller;

import com.example.fitmeal.domain.model.dto.UserProfileDTO;
import com.example.fitmeal.domain.service.UserProfileService;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Meal;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        // Convertir DTO a entidad UserProfile incluyendo todos los campos
        UserProfile userProfile = UserProfile.builder()
                .weight(userProfileDTO.getWeight())
                .height(userProfileDTO.getHeight())
                .goal(userProfileDTO.getGoal())
                .sex(userProfileDTO.getSex())
                .age(userProfileDTO.getAge()) // Asegúrate de asignar la edad
                .activityLevel(userProfileDTO.getActivityLevel()) // Asegúrate de asignar el nivel de actividad
                .build();

        // Validar y guardar el perfil
        String result = userProfileService.validateAndSaveProfile(userId, userProfile);

        // Comprobar si el perfil fue guardado o si hubo alguna advertencia
        if (result.startsWith("It's not recommended")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    // Endpoint para asignar automáticamente el plan de comidas basado en el perfil del usuario
    // POST: Asignar un plan de comidas a un perfil de usuario existente
    @PostMapping("/{userId}/assign-meal-plan")
    public ResponseEntity<String> assignMealPlanToUserProfile(@PathVariable String userId) {
        String result = userProfileService.assignMealPlanToUserProfile(userId);

        if (result.startsWith("Meal plan assigned:")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // GET: Obtener las comidas recomendadas para el día actual
    @GetMapping("/{userId}/recommended-meals")
    public ResponseEntity<List<Meal>> getRecommendedMealsForToday(@PathVariable String userId) {
        try {
            List<Meal> meals = userProfileService.recommendMealsForUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(meals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}