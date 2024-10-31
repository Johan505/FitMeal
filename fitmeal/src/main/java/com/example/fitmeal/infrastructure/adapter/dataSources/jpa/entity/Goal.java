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
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre del objetivo (ej., "ganar músculo", "perder peso")
    private String description; // Descripción del objetivo

    // Distribución recomendada de macronutrientes para este objetivo
    private double recommendedCarbPercentage;
    private double recommendedProteinPercentage;
    private double recommendedFatPercentage;

    // Opcional: Rango de IMC recomendado para este objetivo
    private double minBmi;
    private double maxBmi;

    // Relación con MealPlan (si deseas asociar objetivos a planes específicos)
    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private List<MealPlan> mealPlans;

}
