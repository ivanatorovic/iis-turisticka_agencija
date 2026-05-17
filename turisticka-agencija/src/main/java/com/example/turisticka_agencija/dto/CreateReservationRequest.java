package com.example.turisticka_agencija.dto;

public class CreateReservationRequest {

    private Long userId;
    private Long arrangementId;
    private Long arrangementTermId;
    private int numberOfPassengers;

    public CreateReservationRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public Long getArrangementTermId() {
        return arrangementTermId;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public void setArrangementTermId(Long arrangementTermId) {
        this.arrangementTermId = arrangementTermId;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}