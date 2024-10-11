package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "meal_plans")
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, length = 50)
    private String mealType; // Por ejemplo, 'déficit calórico', 'superávit calórico'

    @Column(nullable = false)
    private double totalCalories;

    @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL)
    private List<UserMealPlan> userMealPlans;
}
