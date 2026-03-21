package com.revapathah.journey_preparation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revapathah.journey_preparation.dto.CreatePreparationPlanRequest;
import com.revapathah.journey_preparation.dto.PreparationPlanResponse;
import com.revapathah.journey_preparation.dto.UpdatePreparationPlanRequest;
import com.revapathah.journey_preparation.dto.UpdateStatusRequest;
import com.revapathah.journey_preparation.service.PreparationPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/users/{userId}/preparation-plans")
@RequiredArgsConstructor
public class PreparationPlanController {

    private final PreparationPlanService service;

    // CREATE
    @PostMapping
    public ResponseEntity<PreparationPlanResponse> create(
            @PathVariable Long userId,
            @Valid @RequestBody CreatePreparationPlanRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createPlan(request));
    }

    // GET ALL (Paginated)
    @GetMapping
    public ResponseEntity<Page<PreparationPlanResponse>> getAll(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {

        return ResponseEntity.ok(service.getAllUserPreparationPlans(userId, pageable));
    }

    // GET BY ID
    @GetMapping("/{planId}")
    public ResponseEntity<PreparationPlanResponse> getById(
            @PathVariable Long userId,
            @PathVariable Long planId) {

        return ResponseEntity.ok(service.getUserPreparationPlanById(userId, planId));
    }

    // UPDATE (Full Update)
    @PutMapping("/{planId}")
    public ResponseEntity<PreparationPlanResponse> update(
            @PathVariable Long userId,
            @PathVariable Long planId,
            @Valid @RequestBody UpdatePreparationPlanRequest request) {

        return ResponseEntity.ok(service.updateUserPreparationPlan(userId, planId, request));
    }

    // UPDATE STATUS (Lifecycle)
    @PatchMapping("/{planId}/status")
    public ResponseEntity<PreparationPlanResponse> updateStatus(
            @PathVariable Long userId,
            @PathVariable Long planId,
            @Valid @RequestBody UpdateStatusRequest request) {

        return ResponseEntity.ok(
                service.updateUserPreparationPlanStatus(userId, planId, request.status()));
    }

    // DELETE
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId,
            @PathVariable Long planId) {

        service.delete(userId, planId);
        return ResponseEntity.noContent().build();
    }
}
