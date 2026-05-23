package com.example.turisticka_agencija.dto;

public class ManagerArrangementRequestDto {

    private String name;
    private String description;
    private double basePrice;
    private int numberOfNights;
    private String imageUrl;

    private Long workflowId;
    private Long managerId;
    private Long destinationId;
    private Long accommodationId;
    private Long transportId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public Long getTransportId() {
        return transportId;
    }
}