package com.example.turisticka_agencija.dto;

import java.time.LocalDate;

public class ArrangementTermResponseDto {
    private Long arrangementTermId;

    private Long arrangementId;
    private String arrangementName;
    private String destinationName;
    private String destinationCountry;
    private Double basePrice;
    private Integer numberOfNights;

    private Long termId;
    private LocalDate startDate;
    private LocalDate endDate;

    private Integer capacity;
    private Integer reservedSpots;
    private Integer availableSpots;

    public ArrangementTermResponseDto() {
    }

    public ArrangementTermResponseDto(
            Long arrangementTermId,
            Long arrangementId,
            String arrangementName,
            String destinationName,
            String destinationCountry,
            Double basePrice,
            Integer numberOfNights,
            Long termId,
            LocalDate startDate,
            LocalDate endDate,
            Integer capacity,
            Integer reservedSpots,
            Integer availableSpots
    ) {
        this.arrangementTermId = arrangementTermId;
        this.arrangementId = arrangementId;
        this.arrangementName = arrangementName;
        this.destinationName = destinationName;
        this.destinationCountry = destinationCountry;
        this.basePrice = basePrice;
        this.numberOfNights = numberOfNights;
        this.termId = termId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.reservedSpots = reservedSpots;
        this.availableSpots = availableSpots;
    }

    public Long getArrangementTermId() {
        return arrangementTermId;
    }

    public void setArrangementTermId(Long arrangementTermId) {
        this.arrangementTermId = arrangementTermId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public String getArrangementName() {
        return arrangementName;
    }

    public void setArrangementName(String arrangementName) {
        this.arrangementName = arrangementName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(Integer numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getReservedSpots() {
        return reservedSpots;
    }

    public void setReservedSpots(Integer reservedSpots) {
        this.reservedSpots = reservedSpots;
    }

    public Integer getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
    }
}