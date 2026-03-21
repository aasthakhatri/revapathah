package com.revapathah.journey_preparation.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreatePreparationPlanRequest(

        @NotBlank(message = "UserId is required")
        Long userId,
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @FutureOrPresent(message = "Start date cannot be in past")
        LocalDate startDate,

        @FutureOrPresent(message = "End date cannot be in past")
        LocalDate endDate

) {}
