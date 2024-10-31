package com.example.fitmeal.domain.service;

import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.domain.port.dao.MealPlanDao;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.MealPlan;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.fitmeal.domain.port.dao.UserProfileDao;
import com.example.fitmeal.domain.port.dao.UserDao;
import com.example.fitmeal.infrastructure.adapter.mapper.UserDboMapper;

import java.util.Optional;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileDao userProfileDao;
    private final UserDao userDao;
    private final UserDboMapper userDboMapper;

    private final MealPlanDao mealPlanDao;

    @Autowired
    public UserProfileService(UserProfileDao userProfileDao, UserDao userDao, MealPlanDao mealPlanDao, UserDboMapper userDboMapper) {
        this.userProfileDao = userProfileDao;
        this.userDao = userDao;
        this.userDboMapper = userDboMapper;
        this.mealPlanDao = mealPlanDao;
    }

    public String validateAndSaveProfile(String userId, UserProfile userProfile) {
        // Calculate BMI
        double bmi = userProfile.getWeight() / (userProfile.getHeight() * userProfile.getHeight());
        userProfile.setImc(bmi);

        // Validate height (between 1.0 and 2.5 meters)
        if (userProfile.getHeight() < 1.0 || userProfile.getHeight() > 2.5) {
            return "The height entered is not realistic. Please enter a value between 1.0 and 2.5 meters.";
        }

        // Validate weight (between 30 and 200 kg)
        if (userProfile.getWeight() < 30 || userProfile.getWeight() > 200) {
            return "The weight entered is not realistic. Please enter a value between 30 and 200 kg.";
        }

        // Validate sex (only accept "male" or "female")
        String sex = userProfile.getSex().toLowerCase();
        if (!sex.equals("male") && !sex.equals("female")) {
            return "Invalid sex. Please choose 'male' or 'female'.";
        }

        // Normalize goal to lowercase for comparison
        String goal = userProfile.getGoal().toLowerCase();

        // Validate the goal (accept specific goals)
        if (!goal.equals("lose weight") && !goal.equals("gain weight") && !goal.equals("maintain weight")
                && !goal.equals("gain muscle") && !goal.equals("definition")) {
            return "Invalid goal. Please choose 'lose weight', 'gain weight', 'maintain weight', 'gain muscle', or 'definition'.";
        }

        // Perform validations based on BMI and weight
        if (bmi < 18.5 && goal.contains("lose")) {
            return "It's not recommended to lose weight as your BMI is below the healthy range (underweight).";
        } else if ((bmi >= 30 || userProfile.getWeight() > 120) && goal.contains("gain") && !goal.equals("gain muscle")) {
            return "It's not recommended to gain weight as you are already in the obesity range.";
        } else if (bmi >= 25 && bmi < 29.9 && goal.contains("gain") && !goal.equals("gain muscle")) {
            return "It's not recommended to gain weight as you are already in the overweight range.";
        }

        // Additional validation for "gain muscle"
        if (goal.equals("gain muscle")) {
            if (bmi >= 25) {
                return "To achieve your goal of gaining muscle, it's recommended to follow a weight loss plan first. Once your BMI is in a healthier range, we will recommend a muscle gain plan.";
            } else if (bmi < 18.5) {
                return "It's recommended to reach a healthy weight before focusing on muscle gain.";
            }
        }

        // Additional validation for "definition"
        if (goal.equals("definition") && bmi > 25) {
            return "It's recommended to first reduce body fat before focusing on definition.";
        }

        // Validate activity level (must be between 1 and 5)
        if (userProfile.getActivityLevel() < 1 || userProfile.getActivityLevel() > 5) {
            return "Activity level must be between 1 and 5.";
        }

        // Calculate and assign calories needed
        double caloriesNeeded = calculateCalories(
                userProfile.getWeight(),
                userProfile.getHeight(),
                userProfile.getSex(),
                userProfile.getAge(),
                userProfile.getActivityLevel()
        );
        userProfile.setCaloriesNeeded(caloriesNeeded);

        // If everything is valid, save the profile
        User user = userDao.getById(userId);
        UserEntity userEntity = userDboMapper.toDbo(user);
        userProfile.setUser(userEntity);
        userProfileDao.createUserProfile(userProfile);

        return "Profile successfully saved.";
    }

    // Method to calculate calories based on weight, height, sex, age, and numeric activity level
    private double calculateCalories(double weight, double height, String sex, int age, int activityLevel) {
        double baseCalories;
        if (sex.equalsIgnoreCase("male")) {
            // Harris-Benedict formula for males
            baseCalories = 66 + (13.7 * weight) + (5 * height * 100) - (6.8 * age);
        } else {
            // Harris-Benedict formula for females
            baseCalories = 655 + (9.6 * weight) + (1.8 * height * 100) - (4.7 * age);
        }

        // Adjust calories based on numeric activity level
        double activityMultiplier;
        switch (activityLevel) {
            case 1:
                activityMultiplier = 1.2; // Sedentary
                break;
            case 2:
                activityMultiplier = 1.375; // Lightly active
                break;
            case 3:
                activityMultiplier = 1.55; // Moderately active
                break;
            case 4:
                activityMultiplier = 1.725; // Very active
                break;
            case 5:
                activityMultiplier = 1.9; // Super active
                break;
            default:
                throw new IllegalArgumentException("Invalid activity level provided.");
        }

        return baseCalories * activityMultiplier;
    }

    public Optional<MealPlan> recommendMealPlan(UserProfile userProfile) {
        // Definir un rango de calorías aceptable (por ejemplo, ±200 calorías)
        double minCalories = userProfile.getCaloriesNeeded() - 200;
        double maxCalories = userProfile.getCaloriesNeeded() + 200;

        // Buscar el plan de alimentación adecuado
        return mealPlanDao.findTopByMealTypeAndTotalCaloriesBetween(userProfile.getGoal(), minCalories, maxCalories);
    }
}