package com.revapathah.journey_preparation.dto;

import java.time.LocalDate;


public record PreparationPlanResponse(
        Long id,
        Long userId,
        String title,
        String description,
        LocalDate startDate,
        Long durationDays,
        LocalDate endDate
          
) {}