package com.example.turisticka_agencija.dto;

import java.time.LocalDate;

public class ArrangementSearchRequest {

    private String destination;
    private LocalDate travelDate;
    private int numberOfPassengers;
    private Double budget;

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
}