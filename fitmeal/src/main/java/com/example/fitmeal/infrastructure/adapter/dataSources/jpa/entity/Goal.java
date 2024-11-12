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

    @Column(nullable = false, unique = true)
    private String name; // "Perder peso", "Mantener peso", "Ganar músculo"

    private String description;

    @OneToMany(mappedBy = "goal")
    private List<MealPlan> mealPlans;
}
