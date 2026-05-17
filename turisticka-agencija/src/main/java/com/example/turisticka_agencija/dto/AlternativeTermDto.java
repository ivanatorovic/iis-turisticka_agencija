package com.example.turisticka_agencija.dto;

import java.time.LocalDate;

public class AlternativeTermDto {

    private Long arrangementTermId;

    private LocalDate startDate;

    private LocalDate endDate;

    private int availableSpots;

    public AlternativeTermDto() {
    }

    public AlternativeTermDto(Long arrangementTermId,
                              LocalDate startDate,
                              LocalDate endDate,
                              int availableSpots) {
        this.arrangementTermId = arrangementTermId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableSpots = availableSpots;
    }

    public Long getArrangementTermId() {
        return arrangementTermId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setArrangementTermId(Long arrangementTermId) {
        this.arrangementTermId = arrangementTermId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }
}