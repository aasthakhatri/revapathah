package com.revapathah;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revapathah.dto.CreatePreparationPlanRequest;
import com.revapathah.dto.PreparationPlanResponse;
import com.revapathah.entity.PreparationPlan;
import com.revapathah.repository.PreparationPlanRepository;
import com.revapathah.service.PreparationPlanService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PreparationPlanServiceTest {

    @Mock
    private PreparationPlanRepository repository;

    @InjectMocks
    private PreparationPlanService service;

    @Test
    void shouldCreatePlan() {

        CreatePreparationPlanRequest request =
                new CreatePreparationPlanRequest(
                        "u1", 90, LocalDate.now());

        PreparationPlan savedEntity = PreparationPlan.builder()
                .id(1L)
                .userId("u1")
                .durationDays(90)
                .startDate(LocalDate.now())
                .build();

        when(repository.save(any())).thenReturn(savedEntity);

        PreparationPlanResponse response =
                service.createPlan(request);

        assertEquals(1L, response.id());
        assertEquals("u1", response.userId());
    }
}

