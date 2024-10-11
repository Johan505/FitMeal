package com.example.fitmeal.domain.service;

import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.fitmeal.domain.port.dao.UserProfileDao;
import com.example.fitmeal.domain.port.dao.UserDao;
import com.example.fitmeal.infrastructure.adapter.mapper.UserDboMapper;
@Service
@Transactional
public class UserProfileService {

    private final UserProfileDao userProfileDao;
    private final UserDao userDao;
    private final UserDboMapper userDboMapper;

    @Autowired
    public UserProfileService(UserProfileDao userProfileDao, UserDao userDao, UserDboMapper userDboMapper) {
        this.userProfileDao = userProfileDao;
        this.userDao = userDao;
        this.userDboMapper = userDboMapper;
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

        // Validate the goal (only accept specific goals)
        if (!goal.equals("lose weight") && !goal.equals("gain weight") && !goal.equals("maintain weight")) {
            return "Invalid goal. Please choose 'lose weight', 'gain weight', or 'maintain weight'.";
        }

        // Perform validations based on BMI and weight
        if (bmi < 18.5 && goal.contains("lose")) {
            return "It's not recommended to lose weight as your BMI is below the healthy range (underweight).";
        } else if ((bmi >= 30 || userProfile.getWeight() > 120) && goal.contains("gain")) {
            return "It's not recommended to gain weight as you are already in the obesity range.";
        } else if (bmi >= 25 && bmi < 29.9 && goal.contains("gain")) {
            return "It's not recommended to gain weight as you are already in the overweight range.";
        }

        // Calculate and assign calories needed
        double caloriesNeeded = calculateCalories(userProfile.getWeight(), userProfile.getHeight(), userProfile.getSex());
        userProfile.setCaloriesNeeded(caloriesNeeded);

        // If everything is valid, save the profile
        User user = userDao.getById(userId);
        UserEntity userEntity = userDboMapper.toDbo(user);
        userProfile.setUser(userEntity);
        userProfileDao.createUserProfile(userProfile);

        return "Profile successfully saved.";
    }

    // Method to calculate calories based on weight, height, and sex
    private double calculateCalories(double weight, double height, String sex) {
        if (sex.equalsIgnoreCase("male")) {
            // Harris-Benedict formula for males
            return 66 + (13.7 * weight) + (5 * height * 100) - (6.8 * 30);  // Example for a 30-year-old male
        } else {
            // Harris-Benedict formula for females
            return 655 + (9.6 * weight) + (1.8 * height * 100) - (4.7 * 30);  // Example for a 30-year-old female
        }
    }
}
