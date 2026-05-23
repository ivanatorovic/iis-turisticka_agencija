package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.AdditionalActivityExecutionRequest;
import com.example.turisticka_agencija.dto.AdditionalActivityExecutionResponse;
import com.example.turisticka_agencija.service.AdditionalActivityExecutionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/additional-activity-executions")
public class AdditionalActivityExecutionController {

    private final AdditionalActivityExecutionService executionService;

    public AdditionalActivityExecutionController(
            AdditionalActivityExecutionService executionService
    ) {
        this.executionService = executionService;
    }

    @GetMapping("/arrangement-term/{arrangementTermId}")
    public List<AdditionalActivityExecutionResponse> getByArrangementTerm(
            @PathVariable Long arrangementTermId
    ) {
        return executionService.getByArrangementTerm(arrangementTermId);
    }

    @PostMapping
    public AdditionalActivityExecutionResponse createExecution(
            @RequestBody AdditionalActivityExecutionRequest request,
            Principal principal
    ) {
        return executionService.createExecution(request, principal);
    }

    @DeleteMapping("/{id}")
    public void deleteExecution(
            @PathVariable Long id,
            Principal principal
    ) {
        executionService.deleteExecution(id, principal);
    }
}