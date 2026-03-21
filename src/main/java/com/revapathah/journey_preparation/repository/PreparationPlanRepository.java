package com.revapathah.journey_preparation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.revapathah.journey_preparation.entity.PreparationPlan;


public interface PreparationPlanRepository
        extends JpaRepository<PreparationPlan, Long> {

    // Fetch paginated plans for a user
    Page<PreparationPlan> findByUser_Id(Long userId, Pageable pageable);

    // Fetch specific plan belonging to a user
    Optional<PreparationPlan> findByIdAndUser_Id(Long planId, Long userId);
}
