package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.service.SalesAnalyticsPdfService;
import com.example.turisticka_agencija.service.SalesAnalyticsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-analytics")
public class SalesAnalyticsController {

    private final SalesAnalyticsService salesAnalyticsService;

    private final SalesAnalyticsPdfService salesAnalyticsPdfService;

    public SalesAnalyticsController(
            SalesAnalyticsService salesAnalyticsService,
            SalesAnalyticsPdfService salesAnalyticsPdfService
    ) {
        this.salesAnalyticsService = salesAnalyticsService;
        this.salesAnalyticsPdfService = salesAnalyticsPdfService;
    }

    @GetMapping("/yearly-summary")
    public YearlySalesSummaryDto getYearlySummary(@RequestParam int year) {
        return salesAnalyticsService.getYearlySummary(year);
    }

    @GetMapping("/revenue-by-years")
    public List<YearRevenueDto> getRevenueByYears() {
        return salesAnalyticsService.getRevenueByYears();
    }

    @GetMapping("/monthly-arrangement")
    public List<MonthlyArrangementSalesDto> getMonthlyArrangementSales(@RequestParam Long arrangementId,
                                                                       @RequestParam int year) {
        return salesAnalyticsService.getMonthlyArrangementSales(arrangementId, year);
    }

    @GetMapping("/popular-destinations")
    public List<PopularDestinationDto> getPopularDestinations(@RequestParam int year) {
        return salesAnalyticsService.getPopularDestinations(year);
    }

    @GetMapping("/popular-arrangements")
    public List<PopularArrangementDto> getPopularArrangements(@RequestParam int year) {
        return salesAnalyticsService.getPopularArrangements(year);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestParam int year,
                                              @RequestParam(required = false) Long arrangementId) {

        System.out.println("USAO U PDF ENDPOINT");

        byte[] pdf = salesAnalyticsPdfService.generateSalesAnalyticsPdf(year, arrangementId);

        System.out.println("VELICINA PDF U KONTROLERU: " + pdf.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=sales-analytics-" + year + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}