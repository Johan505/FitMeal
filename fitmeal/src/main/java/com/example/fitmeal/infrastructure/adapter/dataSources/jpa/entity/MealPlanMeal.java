package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "meal_plan_meals")
public class MealPlanMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_plan_id", referencedColumnName = "id")
    private MealPlan mealPlan;

    @ManyToOne
    @JoinColumn(name = "meal_id", referencedColumnName = "id")
    private Meal meal;

    @Column(nullable = false, length = 50)
    private String mealTime; // desayuno, almuerzo, cena, snack

    @Column(nullable = false)
    private int dayOfWeek; // 1 = Lunes, 2 = Martes, ..., 7 = Domingo
}