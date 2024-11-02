package com.example.fitmeal.domain.service;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class UserMetricsService {

    public void calculateMetrics(UserProfile userProfile) {
        double bmi = calculateBMI(userProfile.getWeight(), userProfile.getHeight());
        userProfile.setImc(bmi);

        double caloriesNeeded = calculateCalories(
                userProfile.getWeight(),
                userProfile.getHeight(),
                userProfile.getSex(),
                userProfile.getAge(),
                userProfile.getActivityLevel()
        );
        userProfile.setCaloriesNeeded(caloriesNeeded);
    }

    public double calculateBMI(double weight, double height) {
        double bmi = weight / (height * height);
        return BigDecimal.valueOf(bmi)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public double calculateCalories(double weight, double height, String sex, int age, int activityLevel) {
        double baseCalories;
        if (sex.equalsIgnoreCase("male")) {
            baseCalories = 66 + (13.7 * weight) + (5 * height * 100) - (6.8 * age);
        } else {
            baseCalories = 655 + (9.6 * weight) + (1.8 * height * 100) - (4.7 * age);
        }

        double activityMultiplier;
        switch (activityLevel) {
            case 1:
                activityMultiplier = 1.2;
                break;
            case 2:
                activityMultiplier = 1.375;
                break;
            case 3:
                activityMultiplier = 1.55;
                break;
            case 4:
                activityMultiplier = 1.725;
                break;
            case 5:
                activityMultiplier = 1.9;
                break;
            default:
                throw new IllegalArgumentException("Invalid activity level provided.");
        }

        double caloriesNeeded = baseCalories * activityMultiplier;
        return BigDecimal.valueOf(caloriesNeeded)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}