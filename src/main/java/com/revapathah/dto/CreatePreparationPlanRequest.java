package com.revapathah.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreatePreparationPlanRequest(

        @NotBlank(message = "UserId is required")
        String userId,

        @Min(value = 1, message = "Duration must be at least 1 day")
        Integer durationDays,

        @FutureOrPresent(message = "Start date cannot be in past")
        LocalDate startDate
) {}
