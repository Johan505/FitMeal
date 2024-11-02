package com.example.fitmeal.domain.port.dao;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MealPlanDao extends JpaRepository<MealPlan, Long> {

    // Asegúrate de que `mealType` es un String y `totalCalories` es un rango Double
    Optional<MealPlan> findTopByMealTypeAndTotalCaloriesBetween(String mealType, double minCalories, double maxCalories);
}

