package com.example.fitmeal.domain.model.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MealDTO {
    private String name;
    private int calories;
    private int protein;
    private int fat;
    private int carbs;
    private String servingsize;
    private String dayOfWeek;
    private String mealType;
}
