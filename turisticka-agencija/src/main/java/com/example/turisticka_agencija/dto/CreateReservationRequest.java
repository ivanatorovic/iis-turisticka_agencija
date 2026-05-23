package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.PaymentType;

public class CreateReservationRequest {

    private Long userId;
    private Long arrangementId;
    private Long arrangementTermId;
    private int numberOfPassengers;

    private PaymentType paymentType;
    private Integer numberOfInstallments;

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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
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

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }
}