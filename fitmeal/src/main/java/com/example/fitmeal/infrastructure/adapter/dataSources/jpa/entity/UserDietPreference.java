package com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "user_diet_preferences")
public class UserDietPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId; // UUID del usuario

    @ManyToOne
    @JoinColumn(name = "diet_type_id", referencedColumnName = "id")
    private DietType dietType;
}