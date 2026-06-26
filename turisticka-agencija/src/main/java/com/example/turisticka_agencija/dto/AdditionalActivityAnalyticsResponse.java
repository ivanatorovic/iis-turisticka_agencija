package com.example.turisticka_agencija.dto;

import java.util.List;

public class AdditionalActivityAnalyticsResponse {
    private AdditionalActivityAnalyticsSummaryDto summary;
    private List<ActivityPopularityDto> popularity;
    private List<ActivityOccupancyDto> occupancy;
    private List<ActivityRevenueDto> revenue;
    private List<ActivityCancellationDto> cancellations;
    private List<GuideWorkloadDto> guideWorkload;
    private List<AdditionalActivityAnalyticsRowDto> tableRows;

    public AdditionalActivityAnalyticsResponse(
            AdditionalActivityAnalyticsSummaryDto summary,
            List<ActivityPopularityDto> popularity,
            List<ActivityOccupancyDto> occupancy,
            List<ActivityRevenueDto> revenue,
            List<ActivityCancellationDto> cancellations,
            List<GuideWorkloadDto> guideWorkload,
            List<AdditionalActivityAnalyticsRowDto> tableRows
    ) {
        this.summary = summary;
        this.popularity = popularity;
        this.occupancy = occupancy;
        this.revenue = revenue;
        this.cancellations = cancellations;
        this.guideWorkload = guideWorkload;
        this.tableRows = tableRows;
    }

    public AdditionalActivityAnalyticsSummaryDto getSummary() { return summary; }
    public List<ActivityPopularityDto> getPopularity() { return popularity; }
    public List<ActivityOccupancyDto> getOccupancy() { return occupancy; }
    public List<ActivityRevenueDto> getRevenue() { return revenue; }
    public List<ActivityCancellationDto> getCancellations() { return cancellations; }
    public List<GuideWorkloadDto> getGuideWorkload() { return guideWorkload; }
    public List<AdditionalActivityAnalyticsRowDto> getTableRows() { return tableRows; }
}