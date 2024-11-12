package com.example.fitmeal.infrastructure.rest.controller;

import com.example.fitmeal.domain.model.dto.command.MealDTO;
import com.example.fitmeal.domain.service.MealPlanService;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meal-plan")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @Autowired
    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    @PostMapping("/assign/{userId}")
    public ResponseEntity<String> assignMealPlanToUserProfile(@PathVariable String userId) {
        String result = mealPlanService.assignMealPlanToUserProfile(userId);

        if (result.startsWith("No suitable meal plan found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Endpoint para obtener las comidas del día
    @GetMapping("/daily-meals/{userId}")
    public ResponseEntity<List<MealDTO>> getMealsForToday(@PathVariable String userId) {
        List<MealDTO> mealsForToday = mealPlanService.getMealsForToday(userId);
        return ResponseEntity.ok(mealsForToday);
    }
    @GetMapping("/weekly-meals/{userId}")
    public ResponseEntity<List<MealDTO>> getWeeklyMealPlan(@PathVariable String userId) {
        try {
            // Llamar al servicio para obtener las comidas
            List<MealDTO> mealsForThisWeek = mealPlanService.getWeeklyMealPlan(userId);
            return ResponseEntity.ok(mealsForThisWeek);
        } catch (IllegalArgumentException e) {
            // Si el perfil del usuario no se encuentra, devolver error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
