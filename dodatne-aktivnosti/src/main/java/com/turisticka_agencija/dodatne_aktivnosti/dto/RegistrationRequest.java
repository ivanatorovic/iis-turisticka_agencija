package com.turisticka_agencija.dodatne_aktivnosti.dto;

public class RegistrationRequest {

    private Long registrationId;
    private Long executionId;
    private Integer numberOfPeople;
    private String status;
    private Integer reservedSpots;
    private Integer capacity;

    public Integer getReservedSpots() {
        return reservedSpots;
    }

    public void setReservedSpots(Integer reservedSpots) {
        this.reservedSpots = reservedSpots;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public RegistrationRequest() {
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}