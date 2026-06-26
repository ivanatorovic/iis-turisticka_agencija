package com.example.turisticka_agencija.dto;

import java.time.LocalDate;

public class AdditionalActivityAnalyticsRowDto {
    private Long arrangementId;
    private String arrangementName;
    private Long arrangementTermId;
    private LocalDate termStartDate;
    private LocalDate termEndDate;
    private Long executionId;
    private String activityName;
    private LocalDate activityDate;
    private String guideName;
    private long registrationsCount;
    private long participantsCount;
    private int capacity;
    private int reservedSpots;
    private double occupancyRate;
    private double revenue;
    private double cancelRate;

    public AdditionalActivityAnalyticsRowDto(
            Long arrangementId,
            String arrangementName,
            Long arrangementTermId,
            LocalDate termStartDate,
            LocalDate termEndDate,
            Long executionId,
            String activityName,
            LocalDate activityDate,
            String guideName,
            long registrationsCount,
            long participantsCount,
            int capacity,
            int reservedSpots,
            double occupancyRate,
            double revenue,
            double cancelRate
    ) {
        this.arrangementId = arrangementId;
        this.arrangementName = arrangementName;
        this.arrangementTermId = arrangementTermId;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
        this.executionId = executionId;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.guideName = guideName;
        this.registrationsCount = registrationsCount;
        this.participantsCount = participantsCount;
        this.capacity = capacity;
        this.reservedSpots = reservedSpots;
        this.occupancyRate = occupancyRate;
        this.revenue = revenue;
        this.cancelRate = cancelRate;
    }

    public Long getArrangementId() { return arrangementId; }
    public String getArrangementName() { return arrangementName; }
    public Long getArrangementTermId() { return arrangementTermId; }
    public LocalDate getTermStartDate() { return termStartDate; }
    public LocalDate getTermEndDate() { return termEndDate; }
    public Long getExecutionId() { return executionId; }
    public String getActivityName() { return activityName; }
    public LocalDate getActivityDate() { return activityDate; }
    public String getGuideName() { return guideName; }
    public long getRegistrationsCount() { return registrationsCount; }
    public long getParticipantsCount() { return participantsCount; }
    public int getCapacity() { return capacity; }
    public int getReservedSpots() { return reservedSpots; }
    public double getOccupancyRate() { return occupancyRate; }
    public double getRevenue() { return revenue; }
    public double getCancelRate() { return cancelRate; }
}