package com.example.fitmeal.infrastructure.rest.controller;

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

    @PostMapping("/{userId}/assign")
    public ResponseEntity<String> assignMealPlanToUserProfile(@PathVariable String userId) {
        String result = mealPlanService.assignMealPlanToUserProfile(userId);

        if (result.startsWith("No suitable meal plan found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{userId}/daily-meals")
    public ResponseEntity<List<Meal>> getMealsForToday(@PathVariable String userId) {
        List<Meal> mealsForToday = mealPlanService.getMealsForToday(userId);
        return ResponseEntity.ok(mealsForToday);
    }

    // Ruta para obtener las comidas de la semana
    @GetMapping("/{userId}/weekly-meals")
    public ResponseEntity<Map<Integer, List<Meal>>> getWeeklyMealPlan(@PathVariable String userId) {
        Map<Integer, List<Meal>> weeklyMealPlan = mealPlanService.getWeeklyMealPlan(userId);
        return ResponseEntity.ok(weeklyMealPlan);
    }
}
