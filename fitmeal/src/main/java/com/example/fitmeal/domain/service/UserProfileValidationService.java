package com.example.fitmeal.domain.service;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Goal;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileValidationService {

    public String validateProfile(UserProfile userProfile) {
        // Validación de altura
        if (userProfile.getHeight() < 1.0 || userProfile.getHeight() > 2.5) {
            return "The height entered is not realistic. Please enter a value between 1.0 and 2.5 meters.";
        }

        // Validación de peso
        if (userProfile.getWeight() < 30 || userProfile.getWeight() > 200) {
            return "The weight entered is not realistic. Please enter a value between 30 and 200 kg.";
        }

        // Validación de sexo
        String sex = userProfile.getSex().toLowerCase();
        if (!sex.equals("male") && !sex.equals("female")) {
            return "Invalid sex. Please choose 'male' or 'female'.";
        }

        // Validación de objetivo basado en la entidad Goal
        Goal goal = userProfile.getGoal();
        if (goal == null) {
            return "Goal is required and must be a valid choice.";
        }

        String goalName = goal.getName().toLowerCase();
        if (!goalName.equals("perder peso") && !goalName.equals("ganar peso") && !goalName.equals("mantener peso")
                && !goalName.equals("ganar músculo") && !goalName.equals("definición") && !goalName.equals("alta proteína")) {
            return "Invalid goal. Please choose 'perder peso', 'ganar peso', 'mantener peso', 'ganar músculo', 'definición', or 'alta proteína'.";
        }

        // Validación de BMI y objetivo
        double bmi = userProfile.getWeight() / (userProfile.getHeight() * userProfile.getHeight());
        if (bmi < 18.5 && goalName.contains("perder")) {
            return "It's not recommended to lose weight as your BMI is below the healthy range (underweight).";
        } else if ((bmi >= 30 || userProfile.getWeight() > 120) && goalName.contains("ganar") && !goalName.equals("ganar músculo")) {
            return "It's not recommended to gain weight as you are already in the obesity range.";
        } else if (bmi >= 25 && bmi < 29.9 && goalName.contains("ganar") && !goalName.equals("ganar músculo")) {
            return "It's not recommended to gain weight as you are already in the overweight range.";
        }

        // Validación de objetivo de ganancia de músculo
        if (goalName.equals("ganar músculo")) {
            if (bmi >= 25) {
                return "To achieve your goal of gaining muscle, it's recommended to follow a weight loss plan first. Once your BMI is in a healthier range, we will recommend a muscle gain plan.";
            } else if (bmi < 18.5) {
                return "It's recommended to reach a healthy weight before focusing on muscle gain.";
            }
        }

        // Validación de definición
        if (goalName.equals("definición")) {
            if (bmi > 25) {
                return "It's recommended to first reduce body fat before focusing on definition.";
            } else if (bmi < 18.5) {
                return "For definition goals, a minimum BMI of 18.5 is recommended.";
            }
        }

        // Validación de alta proteína
        if (goalName.equals("alta proteína")) {
            if (bmi < 18.5) {
                return "For a high protein goal, it’s recommended to reach a healthy weight before focusing on high protein intake.";
            } else if (userProfile.getActivityLevel() < 3) {
                return "A high protein plan is recommended for active individuals. Please ensure an activity level of at least 3.";
            }
        }

        // Validación de nivel de actividad
        if (userProfile.getActivityLevel() < 1 || userProfile.getActivityLevel() > 5) {
            return "Activity level must be between 1 and 5.";
        }

        // Si todo está bien, devolver null
        return null;
    }
}