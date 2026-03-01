package com.revapathah.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revapathah.dto.CreatePreparationPlanRequest;
import com.revapathah.dto.PreparationPlanResponse;
import com.revapathah.service.PreparationPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/preparation/plans")
@RequiredArgsConstructor
public class PreparationPlanController {

    private final PreparationPlanService service;
    
    @PostMapping
    public ResponseEntity<PreparationPlanResponse> create(@Valid @RequestBody CreatePreparationPlanRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPlan(request));
    }
    
}
