package com.example.fitmeal.domain.port.dao;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealPlanDao extends JpaRepository<MealPlan, Long> {

    // Método que busca el plan de alimentación que coincide con el objetivo y un rango de calorías
    Optional<MealPlan> findTopByMealTypeAndTotalCaloriesBetween(String mealType, double minCalories, double maxCalories);
}
