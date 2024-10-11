package com.example.fitmeal.domain.model.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfileDTO {

    @NotNull(message = "El peso es obligatorio.")
    @Positive(message = "El peso debe ser un número positivo.")
    private Double weight;

    @NotNull(message = "La estatura es obligatoria.")
    @Positive(message = "La estatura debe ser un número positivo.")
    private Double height;

    @NotNull(message = "El objetivo es obligatorio.")
    @Size(min = 1, max = 50, message = "El objetivo debe tener entre 1 y 50 caracteres.")
    private String goal;

    @NotNull(message = "El sexo es obligatorio.")
    private String sex;

    @Positive(message = "Las calorías deben ser un número positivo.")
    private double caloriesNeeded;

    @Positive(message = "El IMC debe ser un número positivo.")
    private double imc;
}
