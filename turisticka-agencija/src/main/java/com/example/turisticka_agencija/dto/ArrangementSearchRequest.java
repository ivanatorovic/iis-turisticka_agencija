package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.AccommodationCategory;
import com.example.turisticka_agencija.model.TransportType;

import java.time.LocalDate;

public class ArrangementSearchRequest {

    private String destination;
    private LocalDate travelDate;
    private int numberOfPassengers;
    private Double budget;

    private AccommodationCategory accommodationCategory;
    private TransportType transportType;
    private Integer numberOfNights;
    private String additionalService;
    private String sortByPrice;

    public ArrangementSearchRequest() {
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public Double getBudget() {
        return budget;
    }

    public AccommodationCategory getAccommodationCategory() {
        return accommodationCategory;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public Integer getNumberOfNights() {
        return numberOfNights;
    }

    public String getAdditionalService() {
        return additionalService;
    }

    public String getSortByPrice() {
        return sortByPrice;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setAccommodationCategory(AccommodationCategory accommodationCategory) {
        this.accommodationCategory = accommodationCategory;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public void setNumberOfNights(Integer numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public void setAdditionalService(String additionalService) {
        this.additionalService = additionalService;
    }

    public void setSortByPrice(String sortByPrice) {
        this.sortByPrice = sortByPrice;
    }
}