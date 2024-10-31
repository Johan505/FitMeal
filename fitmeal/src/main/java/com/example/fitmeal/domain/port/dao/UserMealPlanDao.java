package com.example.fitmeal.domain.port.dao;

import com.example.fitmeal.infrastructure.adapter.dataSources.jpa.entity.UserMealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMealPlanDao extends JpaRepository<UserMealPlan, Long> {
}
