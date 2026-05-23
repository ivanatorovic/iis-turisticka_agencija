package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.service.SalesAnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-analytics")
public class SalesAnalyticsController {

    private final SalesAnalyticsService salesAnalyticsService;

    public SalesAnalyticsController(SalesAnalyticsService salesAnalyticsService) {
        this.salesAnalyticsService = salesAnalyticsService;
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
}