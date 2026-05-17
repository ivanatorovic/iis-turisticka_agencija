package com.example.turisticka_agencija.dto;

import java.util.List;

public class AvailabilityResponse {

    private boolean available;

    private int availableSpots;

    private List<AlternativeTermDto> alternativeTerms;

    public AvailabilityResponse() {
    }

    public AvailabilityResponse(boolean available,
                                int availableSpots,
                                List<AlternativeTermDto> alternativeTerms) {
        this.available = available;
        this.availableSpots = availableSpots;
        this.alternativeTerms = alternativeTerms;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public List<AlternativeTermDto> getAlternativeTerms() {
        return alternativeTerms;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public void setAlternativeTerms(List<AlternativeTermDto> alternativeTerms) {
        this.alternativeTerms = alternativeTerms;
    }
}