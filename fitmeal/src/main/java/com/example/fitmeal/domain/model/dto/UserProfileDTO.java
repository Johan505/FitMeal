package com.example.fitmeal.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfileDTO {

    @NotNull(message = "Weight is required.")
    @Positive(message = "Weight must be a positive number.")
    private Double weight;

    @NotNull(message = "Height is required.")
    @Positive(message = "Height must be a positive number.")
    private Double height;

    @NotNull(message = "Goal is required.")
    @Size(min = 1, max = 50, message = "Goal must be between 1 and 50 characters.")
    private String goal;

    @NotNull(message = "Sex is required.")
    private String sex;

    @NotNull(message = "Age is required.")
    @Min(value = 1, message = "Age must be greater than 0.")
    private Integer age;

    @NotNull(message = "Activity level is required.")
    private Integer activityLevel;
}
