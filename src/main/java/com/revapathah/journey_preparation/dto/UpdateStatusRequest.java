package com.revapathah.journey_preparation.dto;

import com.revapathah.journey_preparation.domain.PreparationPlanStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull PreparationPlanStatus status
) {}
