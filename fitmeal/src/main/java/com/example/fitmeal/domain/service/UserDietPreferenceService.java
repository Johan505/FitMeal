package com.example.fitmeal.domain.service;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.DietType;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserDietPreference;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository.DietTypeRepository;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.repository.UserDietPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDietPreferenceService {

    @Autowired
    private DietTypeRepository dietTypeRepository;

    @Autowired
    private UserDietPreferenceRepository userDietPreferenceRepository;

    public String registerDietPreference(String userId, Long dietTypeId) {
        // Verificar si el tipo de dieta existe por ID
        Optional<DietType> dietTypeOptional = dietTypeRepository.findById(dietTypeId);
        if (dietTypeOptional.isEmpty()) {
            throw new IllegalArgumentException("El tipo de dieta no es válido.");
        }

        // Crear la preferencia de dieta solo si no existe para el usuario
        if (!userDietPreferenceRepository.existsByUserId(userId)) {
            UserDietPreference preference = new UserDietPreference();
            preference.setUserId(userId);
            preference.setDietType(dietTypeOptional.get());
            userDietPreferenceRepository.save(preference);
            return "Preferencia de dieta registrada con éxito.";
        } else {
            return "La preferencia de dieta ya está registrada y no se puede modificar.";
        }
    }

}
