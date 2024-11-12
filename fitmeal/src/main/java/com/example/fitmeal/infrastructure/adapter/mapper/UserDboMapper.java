package com.example.fitmeal.infrastructure.adapter.mapper;

import com.example.fitmeal.domain.model.entity.User;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserEntity;
import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserDboMapper {

    // Mapper de User a UserEntity (cuando el dominio se mapea a la base de datos)
    public UserEntity toDbo(User domain){
        if(domain == null){
            return null;
        }

        // Necesitas asociar el UserProfile correctamente
        UserProfile userProfile = null; // Aquí asignas el UserProfile si está disponible

        // Crear el objeto UserEntity con todos los parámetros
        return new UserEntity(domain.getId(), domain.getFirstName(), domain.getLastName(),
                domain.getEmail(), domain.getPassword(), domain.getIsVerified(),
                userProfile);  // Asegúrate de incluir UserProfile en el constructor
    }

    // Método para crear un UserEntity nuevo
    public UserEntity toDboCreate(User domain){
        if(domain == null){
            return null;
        }

        // Aquí también asignamos el UserProfile si es necesario
        UserProfile userProfile = null; // Asignación del UserProfile si está disponible

        // Crear el objeto UserEntity con todos los parámetros
        return new UserEntity(domain.getId(), domain.getFirstName(), domain.getLastName(),
                domain.getEmail(), domain.getPassword(), domain.getIsVerified(),
                userProfile);  // Asegúrate de incluir UserProfile
    }

    // Mapper de UserEntity a User (cuando el objeto de base de datos se mapea al dominio)
    public User toDomain(UserEntity entity){
        if(entity == null){
            return null;
        }

        // Aquí, puedes recuperar el perfil del usuario si es necesario
        UserProfile userProfile = entity.getUserProfile(); // Obtener el perfil del usuario

        // Crear y devolver el objeto User
        return new User(entity.getId(), entity.getEmail(), entity.getFirstName(),
                entity.getLastName(), entity.getPassword(), entity.isVerified());
    }
}
