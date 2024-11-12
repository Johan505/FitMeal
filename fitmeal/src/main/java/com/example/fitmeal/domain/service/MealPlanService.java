package com.example.fitmeal.domain.service;

import com.example.fitmeal.domain.model.dto.command.MealDTO;
import com.example.fitmeal.domain.port.dao.MealPlanDao;
import com.example.fitmeal.domain.port.dao.UserMealPlanDao;
import com.example.fitmeal.domain.port.dao.UserProfileDao;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.*;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository.GoalRepository;
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
    private final GoalRepository goalRepository;  // Inyecta el GoalRepository

    @Autowired
    public MealPlanService(MealPlanDao mealPlanDao, UserProfileDao userProfileDao, UserMealPlanDao userMealPlanDao, GoalRepository goalRepository) {
        this.mealPlanDao = mealPlanDao;
        this.userProfileDao = userProfileDao;
        this.userMealPlanDao = userMealPlanDao;
        this.goalRepository = goalRepository;  // Inicializa GoalRepository
    }

    public String assignMealPlanToUserProfile(String userId) {
        Optional<UserProfile> userProfileOpt = userProfileDao.findByUser_Id(userId);
        if (userProfileOpt.isEmpty()) {
            throw new IllegalArgumentException("User profile not found.");
        }

        UserProfile userProfile = userProfileOpt.get();
        double caloriesNeeded = userProfile.getCaloriesNeeded();

        // Obtener goal_id del perfil de usuario
        Long goalId = userProfile.getGoal().getId();

        // Definir un margen de tolerancia de 50 calorías para buscar planes de comida
        double minCalories = caloriesNeeded - 50;
        double maxCalories = caloriesNeeded + 50;

        // Buscar el plan de comida dentro del rango de calorías y con el goal_id
        Optional<MealPlan> recommendedMealPlan = mealPlanDao.findTopByGoal_IdAndTotalCaloriesBetween(
                goalId, minCalories, maxCalories);

        if (recommendedMealPlan.isPresent()) {
            MealPlan mealPlan = recommendedMealPlan.get();

            // Crear la asignación del plan de comida al usuario
            UserMealPlan userMealPlan = UserMealPlan.builder()
                    .user(userProfile.getUser())
                    .mealPlan(mealPlan)
                    .personalizedCalories(caloriesNeeded)
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
    public List<MealDTO> getMealsForToday(String userId) {
        try {
            Optional<UserProfile> userProfileOpt = userProfileDao.findByUser_Id(userId);
            if (userProfileOpt.isEmpty()) {
                throw new IllegalArgumentException("User profile not found.");
            }

            UserProfile userProfile = userProfileOpt.get();
            UserMealPlan currentMealPlan = userProfile.getCurrentMealPlan();
            if (currentMealPlan == null || currentMealPlan.getMealPlan() == null) {
                throw new IllegalArgumentException("Meal plan not found for the user.");
            }

            int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
            String[] daysOfWeek = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
            String dayName = daysOfWeek[dayOfWeek];

            return currentMealPlan.getMealPlan().getMealPlanMeals().stream()
                    .filter(mealPlanMeal -> mealPlanMeal.getDayOfWeek() == dayOfWeek)
                    .map(mealPlanMeal -> {
                        Meal meal = mealPlanMeal.getMeal();
                        if (meal == null) {
                            throw new IllegalArgumentException("Meal not found for meal plan entry.");
                        }

                        String mealType = mealPlanMeal.getMealTime();
                        return new MealDTO(
                                meal.getName(),
                                (int) meal.getCalories(),
                                (int) meal.getProtein(),
                                (int) meal.getFat(),
                                (int) meal.getCarbs(),
                                meal.getServingsize(),
                                dayName,
                                mealType
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving meals for today.", e); // Reemplaza con la excepción adecuada
        }
    }





    // Nuevo método para obtener las comidas de la semana
    public List<MealDTO> getWeeklyMealPlan(String userId) {
        Optional<UserProfile> userProfileOpt = userProfileDao.findByUser_Id(userId);
        if (userProfileOpt.isEmpty()) {
            throw new IllegalArgumentException("User profile not found.");
        }

        UserProfile userProfile = userProfileOpt.get();
        UserMealPlan currentMealPlan = userProfile.getCurrentMealPlan();

        // Array con los nombres de los días de la semana
        String[] daysOfWeek = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        // Filtrar y mapear las comidas a MealDTO para todos los días de la semana
        return currentMealPlan.getMealPlan().getMealPlanMeals().stream()
                .map(mealPlanMeal -> {
                    Meal meal = mealPlanMeal.getMeal();
                    String mealType = mealPlanMeal.getMealTime(); // Obtener el tipo de comida (Desayuno, Almuerzo, etc.)
                    String dayName = daysOfWeek[mealPlanMeal.getDayOfWeek()]; // Obtener el nombre del día de la semana
                    return new MealDTO(meal.getName(),
                            (int) meal.getCalories(),
                            (int) meal.getProtein(),
                            (int) meal.getFat(),
                            (int) meal.getCarbs(),
                            meal.getServingsize(),
                            dayName,
                            mealType); // Incluir tipo de comida y día de la semana
                })
                .collect(Collectors.toList());
    }



}