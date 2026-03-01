package com.revapathah.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revapathah.entity.PreparationPlan;

public interface PreparationPlanRepository 
        extends JpaRepository<PreparationPlan, Long> {
}
