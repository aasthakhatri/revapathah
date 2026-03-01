package com.revapathah.dto;

import java.time.LocalDate;

public record PreparationPlanResponse(
        Long id,
        String userId,
        Integer durationDays,
        LocalDate startDate
) {}