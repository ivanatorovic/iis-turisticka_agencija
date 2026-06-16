package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.AdditionalActivityExecutionRequest;
import com.example.turisticka_agencija.dto.AdditionalActivityExecutionResponse;
import com.example.turisticka_agencija.dto.AdditionalActivityExecutionUpdateRequest;
import com.example.turisticka_agencija.service.AdditionalActivityExecutionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
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

    @GetMapping("/guide")
    public List<AdditionalActivityExecutionResponse> getGuideActivities(
            Principal principal
    ) {
        return executionService.getGuideActivities(principal);
    }

    @PutMapping("/{id}")
    public AdditionalActivityExecutionResponse updateExecution(
            @PathVariable Long id,
            @RequestBody AdditionalActivityExecutionUpdateRequest request,
            Principal principal
    ) {
        return executionService.updateExecution(id, request, principal);
    }

    @PutMapping("/{id}/start")
    public AdditionalActivityExecutionResponse startExecution(
            @PathVariable Long id,
            Principal principal
    ) {
        return executionService.startExecution(id, principal);
    }

    @PutMapping("/{id}/finish")
    public AdditionalActivityExecutionResponse finishExecution(
            @PathVariable Long id,
            Principal principal
    ) {
        return executionService.finishExecution(id, principal);
    }

    @GetMapping("/arrangement-term/{arrangementTermId}/filter")
    public List<AdditionalActivityExecutionResponse> getFilteredByArrangementTerm(
            @PathVariable Long arrangementTermId,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) Integer minAvailableSpots,
            @RequestParam(required = false) Boolean onlyAvailable
    ) {
        return executionService.getFilteredByArrangementTerm(
                arrangementTermId,
                dateFrom,
                dateTo,
                minPrice,
                maxPrice,
                minDuration,
                maxDuration,
                minAvailableSpots,
                onlyAvailable
        );
    }

    @PutMapping("/{id}/prior")
    public AdditionalActivityExecutionResponse setPrior(
            @PathVariable Long id,
            @RequestParam boolean prior,
            Principal principal
    ) {
        return executionService.setPrior(id, prior, principal);
    }

    @GetMapping("/arrangement-term/{arrangementTermId}/recommended-sorted")
    public List<AdditionalActivityExecutionResponse> getRecommendedSortedByArrangementTerm(
            @PathVariable Long arrangementTermId,
            Principal principal
    ) {
        return executionService.getRecommendedSortedByArrangementTerm(arrangementTermId, principal);
    }
}