package com.revapathah.journey_preparation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revapathah.journey_preparation.dto.CreatePreparationPlanRequest;
import com.revapathah.journey_preparation.dto.PreparationPlanResponse;
import com.revapathah.journey_preparation.entity.PreparationPlan;
import com.revapathah.journey_preparation.entity.User;
import com.revapathah.journey_preparation.repository.PreparationPlanRepository;
import com.revapathah.journey_preparation.repository.UserRepository;
import com.revapathah.journey_preparation.service.PreparationPlanService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PreparationPlanServiceTest {

    @Mock
    private PreparationPlanRepository preparationPlanRepository;

     @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PreparationPlanService service;

    @Test
void shouldCreatePlan() {

    // Arrange
    Long userId = 11L;   // single source of truth

    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(10);

    CreatePreparationPlanRequest request =
            new CreatePreparationPlanRequest(
                    userId,
                    "Title",
                    "Test description",
                    startDate,
                    endDate
            );

    User mockUser = User.builder()
            .id(userId)
            .name("Broonie")
            .email("broonie@test.com")
            .build();

    PreparationPlan savedEntity = PreparationPlan.builder()
            .id(1L)
            .title("Title")
            .startDate(startDate)
            .endDate(endDate)
            .durationDays(10L)
            .user(mockUser)
            .build();

    when(userRepository.findById(userId))  // ✅ matches request
            .thenReturn(Optional.of(mockUser));

    when(preparationPlanRepository.save(any(PreparationPlan.class)))
            .thenReturn(savedEntity);

    // Act
    PreparationPlanResponse response = service.createPlan(request);

    // Assert
    assertEquals(1L, response.id());
}
}