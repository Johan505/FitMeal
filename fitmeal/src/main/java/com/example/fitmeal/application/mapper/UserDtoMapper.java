package com.example.fitmeal.application.mapper;

import com.example.fitmeal.domain.model.dto.UserDto;
import com.example.fitmeal.domain.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "isVerified", target = "verified")
    UserDto toDto(User domain);
}
