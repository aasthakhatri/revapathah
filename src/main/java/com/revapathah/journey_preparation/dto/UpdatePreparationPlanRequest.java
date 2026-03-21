package com.revapathah.journey_preparation.dto;

import java.time.LocalDate;

public record UpdatePreparationPlanRequest(
        String title,
        String description,
        Long durationDays,
        LocalDate startDate,
        LocalDate endDate
) {}
