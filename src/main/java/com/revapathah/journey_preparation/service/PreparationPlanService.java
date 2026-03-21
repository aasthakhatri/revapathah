package com.revapathah.journey_preparation.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.revapathah.journey_preparation.domain.PreparationPlanStatus;
import com.revapathah.journey_preparation.dto.CreatePreparationPlanRequest;
import com.revapathah.journey_preparation.dto.PreparationPlanResponse;
import com.revapathah.journey_preparation.dto.UpdatePreparationPlanRequest;
import com.revapathah.journey_preparation.entity.PreparationPlan;
import com.revapathah.journey_preparation.entity.User;
import com.revapathah.journey_preparation.repository.PreparationPlanRepository;
import com.revapathah.journey_preparation.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreparationPlanService {

    private final PreparationPlanRepository repository;
    private final UserRepository userRepository;   

    /* =========================================================
       CREATE
       ========================================================= */

    public PreparationPlanResponse createPlan(
            CreatePreparationPlanRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + request.userId())
                );

        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalStateException("End date cannot be before start date");
        }

        long duration = ChronoUnit.DAYS.between(
                request.startDate(),
                request.endDate()
        );

        PreparationPlan entity = PreparationPlan.builder()
                .user(user)
                .title(request.title())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .durationDays(duration)
                .status(PreparationPlanStatus.PLANNED)
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(repository.save(entity));
    }

    /* =========================================================
       GET BY ID (Scoped to User)
       ========================================================= */

    public PreparationPlanResponse getUserPreparationPlanById(
            Long userId,
            Long planId) {

        PreparationPlan plan = repository
                .findByIdAndUser_Id(planId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Plan not found")
                );

        return mapToResponse(plan);
    }

    /* =========================================================
       GET ALL FOR USER
       ========================================================= */

    public Page<PreparationPlanResponse> getAllUserPreparationPlans(
            Long userId,
            Pageable pageable) {

        return repository.findByUser_Id(userId, pageable)
                .map(this::mapToResponse);
    }

    /* =========================================================
       UPDATE PLAN
       ========================================================= */

    public PreparationPlanResponse updateUserPreparationPlan(
            Long userId,
            Long planId,
            UpdatePreparationPlanRequest request) {

        PreparationPlan plan = repository
                .findByIdAndUser_Id(planId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Plan not found")
                );

        if (plan.getStatus() == PreparationPlanStatus.COMPLETED) {
            throw new IllegalStateException("Completed plan cannot be modified");
        }

        if (request.title() != null) {
            plan.setTitle(request.title());
        }

        if (request.description() != null) {
            plan.setDescription(request.description());
        }

        if (request.startDate() != null) {
            plan.setStartDate(request.startDate());
        }

        if (request.endDate() != null) {
            if (request.endDate().isBefore(plan.getStartDate())) {
                throw new IllegalStateException("End date cannot be before start date");
            }
            plan.setEndDate(request.endDate());

            long duration = ChronoUnit.DAYS.between(
                    plan.getStartDate(),
                    plan.getEndDate()
            );
            plan.setDurationDays(duration);
        }

        plan.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(repository.save(plan));
    }

    /* =========================================================
       UPDATE STATUS
       ========================================================= */

    public PreparationPlanResponse updateUserPreparationPlanStatus(
            Long userId,
            Long planId,
            PreparationPlanStatus newStatus) {

        PreparationPlan plan = repository
                .findByIdAndUser_Id(planId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Plan not found")
                );

        validateTransition(plan.getStatus(), newStatus);

        plan.setStatus(newStatus);
        plan.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(repository.save(plan));
    }

    /* =========================================================
       DELETE
       ========================================================= */

    public void delete(Long userId, Long planId) {

        PreparationPlan plan = repository
                .findByIdAndUser_Id(planId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Plan not found")
                );

        if (plan.getStatus() == PreparationPlanStatus.IN_PROGRESS) {
            throw new IllegalStateException("Cannot delete active plan");
        }

        repository.delete(plan);
    }

    /* =========================================================
       MAPPER
       ========================================================= */

    private PreparationPlanResponse mapToResponse(PreparationPlan plan) {

        return new PreparationPlanResponse(
                plan.getId(),
                plan.getUser().getId(),      
                plan.getTitle(),
                plan.getDescription(), plan.getStartDate(),
                plan.getDurationDays(),
                plan.getEndDate()
        );
    }

    /* =========================================================
       VALIDATION
       ========================================================= */

    private void validateTransition(
            PreparationPlanStatus current,
            PreparationPlanStatus next) {

        if (current == PreparationPlanStatus.COMPLETED) {
            throw new IllegalStateException("Completed plan cannot change status");
        }

        if (current == PreparationPlanStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled plan cannot change status");
        }

        if (current == next) {
            throw new IllegalStateException("Plan already in status " + next);
        }

        if (!current.canTransitionTo(next)) {
            throw new IllegalStateException("Invalid status transition");
        }
    }
}
