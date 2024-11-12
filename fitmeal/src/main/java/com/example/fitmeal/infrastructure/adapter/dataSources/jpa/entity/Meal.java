package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double calories;

    @Column(nullable = false)
    private double protein;

    @Column(nullable = false)
    private double fat;

    @Column(nullable = false)
    private String servingsize;

    @Column(nullable = false)
    private double carbs;

    @ManyToOne
    @JoinColumn(name = "meal_type_id", referencedColumnName = "id")
    private MealType mealType;
}
