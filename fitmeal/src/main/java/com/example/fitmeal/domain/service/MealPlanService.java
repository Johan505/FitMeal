package com.example.fitmeal.domain.service;

import com.example.fitmeal.domain.port.dao.MealPlanDao;
import com.example.fitmeal.domain.port.dao.UserMealPlanDao;
import com.example.fitmeal.domain.port.dao.UserProfileDao;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Meal;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.MealPlan;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.MealPlanMeal;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserMealPlan;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

    private final MealPlanDao mealPlanDao;
    private final UserProfileDao userProfileDao;
    private final UserMealPlanDao userMealPlanDao;

    @Autowired
    public MealPlanService(MealPlanDao mealPlanDao, UserProfileDao userProfileDao, UserMealPlanDao userMealPlanDao) {
        this.mealPlanDao = mealPlanDao;
        this.userProfileDao = userProfileDao;
        this.userMealPlanDao = userMealPlanDao;
    }

    public String assignMealPlanToUserProfile(String userId) {
        Optional<UserProfile> userProfileOpt = userProfileDao.findByUser_Id(userId);
        if (userProfileOpt.isEmpty()) {
            throw new IllegalArgumentException("User profile not found.");
        }

        UserProfile userProfile = userProfileOpt.get();
        double minCalories = userProfile.getCaloriesNeeded() - 500;
        double maxCalories = userProfile.getCaloriesNeeded() + 500;

        String mealType = userProfile.getGoal(); // El objetivo se convierte en el tipo de comida aquí

        Optional<MealPlan> recommendedMealPlan = mealPlanDao.findTopByMealTypeAndTotalCaloriesBetween(
                mealType, minCalories, maxCalories);

        if (recommendedMealPlan.isPresent()) {
            MealPlan mealPlan = recommendedMealPlan.get();
            UserMealPlan userMealPlan = UserMealPlan.builder()
                    .user(userProfile.getUser())
                    .mealPlan(mealPlan)
                    .personalizedCalories(userProfile.getCaloriesNeeded())
                    .build();

            userMealPlanDao.save(userMealPlan);
            userProfile.setCurrentMealPlan(userMealPlan);
            userProfileDao.createUserProfile(userProfile);

            return "Meal plan assigned: " + mealPlan.getName();
        } else {
            return "No suitable meal plan found.";
        }
    }

    // Nuevo método para obtener las comidas del día
    public List<Meal> getMealsForToday(String userId) {
        Optional<UserProfile> userProfileOpt = userProfileDao.findByUser_Id(userId);
        if (userProfileOpt.isEmpty()) {
            throw new IllegalArgumentException("User profile not found.");
        }

        UserProfile userProfile = userProfileOpt.get();
        UserMealPlan currentMealPlan = userProfile.getCurrentMealPlan();
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        return currentMealPlan.getMealPlan().getMealPlanMeals().stream()
                .filter(mealPlanMeal -> mealPlanMeal.getDayOfWeek() == dayOfWeek)
                .map(MealPlanMeal::getMeal)
                .collect(Collectors.toList());
    }

    // Nuevo método para obtener las comidas de la semana
    public Map<Integer, List<Meal>> getWeeklyMealPlan(String userId) {
        Optional<UserProfile> userProfileOpt = userProfileDao.findByUser_Id(userId);
        if (userProfileOpt.isEmpty()) {
            throw new IllegalArgumentException("User profile not found.");
        }

        UserProfile userProfile = userProfileOpt.get();
        UserMealPlan userMealPlan = userProfile.getCurrentMealPlan();

        List<MealPlanMeal> mealPlanMeals = userMealPlan.getMealPlan().getMealPlanMeals();

        Map<Integer, List<Meal>> weeklyMeals = new HashMap<>();
        for (int day = 1; day <= 7; day++) {
            final int currentDay = day;
            List<Meal> mealsForDay = mealPlanMeals.stream()
                    .filter(mealPlanMeal -> mealPlanMeal.getDayOfWeek() == currentDay)
                    .map(MealPlanMeal::getMeal)
                    .collect(Collectors.toList());
            weeklyMeals.put(day, mealsForDay);
        }
        return weeklyMeals;
    }
}