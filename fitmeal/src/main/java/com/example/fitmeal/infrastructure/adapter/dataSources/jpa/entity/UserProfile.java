package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double weight;
    private double height;
    private String sex;
    private double caloriesNeeded;
    private double imc;
    private int age;
    private int activityLevel;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "current_meal_plan_id", referencedColumnName = "id")
    private UserMealPlan currentMealPlan;

    // Relación con la entidad Goal
    @ManyToOne
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private Goal goal;  // Relación con la tabla Goal

    // Otros métodos y getters/setters
}
