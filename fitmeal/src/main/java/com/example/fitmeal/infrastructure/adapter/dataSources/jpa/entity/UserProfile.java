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

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false, length = 50)
    private String goal;

    @Column(nullable = false, length = 10)
    private String sex;

    @Column(nullable = false)
    private double caloriesNeeded;

    @Column(nullable = false)
    private double imc;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

}

