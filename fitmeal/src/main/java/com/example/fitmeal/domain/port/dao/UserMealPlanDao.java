package com.example.fitmeal.domain.port.dao;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Meal;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserMealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMealPlanDao extends JpaRepository<UserMealPlan, Long> {

    @Query("SELECT m.meal FROM MealPlanMeal m WHERE m.mealPlan.id = :mealPlanId AND m.dayOfWeek = :dayOfWeek")
    List<Meal> findMealsByMealPlanAndDayOfWeek(@Param("mealPlanId") Long mealPlanId, @Param("dayOfWeek") int dayOfWeek);
}