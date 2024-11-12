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
@Table(name = "meal_types")
public class MealType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // desayuno, almuerzo, cena, snack

    private String description;

    @OneToMany(mappedBy = "mealType")
    private List<Meal> meals;
}
