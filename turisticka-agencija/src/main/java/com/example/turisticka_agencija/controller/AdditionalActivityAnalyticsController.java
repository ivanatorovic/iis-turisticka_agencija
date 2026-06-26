package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.AdditionalActivityAnalyticsResponse;
import com.example.turisticka_agencija.service.AdditionalActivityAnalyticsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/manager/reports/additional-activities")
public class AdditionalActivityAnalyticsController {

    private final AdditionalActivityAnalyticsService analyticsService;

    public AdditionalActivityAnalyticsController(
            AdditionalActivityAnalyticsService analyticsService
    ) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public AdditionalActivityAnalyticsResponse getAdditionalActivityAnalytics(
            @RequestParam Long arrangementId,
            @RequestParam(required = false) Long arrangementTermId,
            Principal principal
    ) {
        return analyticsService.getAnalytics(
                arrangementId,
                arrangementTermId,
                principal
        );
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getAdditionalActivityAnalyticsPdf(
            @RequestParam Long arrangementId,
            @RequestParam(required = false) Long arrangementTermId,
            Principal principal
    ) {
        byte[] pdf = analyticsService.generatePdfReport(
                arrangementId,
                arrangementTermId,
                principal
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=izvestaj-dodatne-aktivnosti.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}