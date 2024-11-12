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

    private String name;
    private String description;
    private String mealType; // Por ejemplo, "déficit calórico" o "superávit calórico"
    private double totalCalories;

    private double carbPercentage;
    private double proteinPercentage;
    private double fatPercentage;

    @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL)
    private List<MealPlanMeal> mealPlanMeals;

    @ManyToOne
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "diet_type_id", referencedColumnName = "id")
    private DietType dietType;
}
