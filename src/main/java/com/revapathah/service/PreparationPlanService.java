package com.revapathah.service;

import org.springframework.stereotype.Service;

import com.revapathah.dto.CreatePreparationPlanRequest;
import com.revapathah.dto.PreparationPlanResponse;
import com.revapathah.entity.PreparationPlan;
import com.revapathah.repository.PreparationPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreparationPlanService {

    private final PreparationPlanRepository repository;

    public PreparationPlanResponse createPlan(CreatePreparationPlanRequest request){
        PreparationPlan entity = PreparationPlan.builder()
                .userId(request.userId())
                .durationDays(request.durationDays())
                .startDate(request.startDate())
                .build();

        PreparationPlan saved = repository.save(entity);

        return new PreparationPlanResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getDurationDays(),
                saved.getStartDate()
        );
      
    } 
    
}
