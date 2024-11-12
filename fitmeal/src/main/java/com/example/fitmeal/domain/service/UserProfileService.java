package com.example.fitmeal.domain.service;

import com.example.fitmeal.domain.model.dto.UserProfileDTO;
import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.domain.port.dao.UserDao;
import com.example.fitmeal.domain.port.dao.UserProfileDao;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.Meal;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import com.example.fitmeal.infrastructure.adapter.mapper.UserDboMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileDao userProfileDao;
    private final UserDao userDao;
    private final UserDboMapper userDboMapper;
    private final UserMetricsService userMetricsService;
    private final UserProfileValidationService userProfileValidationService;
    private final MealPlanService mealPlanService;

    @Autowired
    public UserProfileService(UserProfileDao userProfileDao, UserDao userDao, UserDboMapper userDboMapper,
                              UserMetricsService userMetricsService, UserProfileValidationService userProfileValidationService,
                              MealPlanService mealPlanService) {
        this.userProfileDao = userProfileDao;
        this.userDao = userDao;
        this.userDboMapper = userDboMapper;
        this.userMetricsService = userMetricsService;
        this.userProfileValidationService = userProfileValidationService;
        this.mealPlanService = mealPlanService;
    }

    public String validateAndSaveProfile(String userId, UserProfile userProfile) {
        // Validación del perfil
        String validationMessage = userProfileValidationService.validateProfile(userProfile);
        if (validationMessage != null) {
            return validationMessage;
        }

        // Cálculo de métricas de perfil
        userMetricsService.calculateMetrics(userProfile);

        // Asociar usuario y guardar perfil
        User user = userDao.getById(userId);
        UserEntity userEntity = userDboMapper.toDbo(user);
        userProfile.setUser(userEntity);
        userProfileDao.createUserProfile(userProfile);

        return "Profile successfully saved.";
    }

    public String assignMealPlanToUserProfile(String userId) {
        return mealPlanService.assignMealPlanToUserProfile(userId);
    }

    public Optional<UserProfileDTO> getUserProfileByUserId(String userId) {
        return userProfileDao.findByUser_Id(userId)
                .map(userProfile -> new UserProfileDTO(
                        userProfile.getWeight(),
                        userProfile.getHeight(),
                        userProfile.getGoal().getId(), // ID del objetivo
                        userProfile.getSex(),
                        userProfile.getAge(),
                        userProfile.getActivityLevel(),
                        userProfile.getCaloriesNeeded(), // Calorías necesarias
                        userProfile.getImc(), // IMC
                        userProfile.getCurrentMealPlan() != null ? userProfile.getCurrentMealPlan().getId() : null // ID del plan de comida actual
                ));
    }

}
