package com.example.turisticka_agencija.projection;

import java.time.LocalDate;

public interface AdditionalActivityAnalyticsProjection {

    Long getArrangementId();

    String getArrangementName();

    Long getArrangementTermId();

    LocalDate getTermStartDate();

    LocalDate getTermEndDate();

    Long getExecutionId();

    String getActivityName();

    LocalDate getActivityDate();

    String getGuideName();

    Long getRegistrationsCount();

    Long getParticipantsCount();

    Integer getCapacity();

    Integer getReservedSpots();

    Double getOccupancyRate();

    Double getRevenue();

    Double getCancelRate();
}