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

    // Distribución de macronutrientes en porcentaje
    private double carbPercentage;
    private double proteinPercentage;
    private double fatPercentage;

    @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL)
    private List<UserMealPlan> userMealPlans;

    @ManyToOne
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private Goal goal;

}