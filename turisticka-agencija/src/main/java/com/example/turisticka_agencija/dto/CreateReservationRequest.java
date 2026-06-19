package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.PaymentType;

import java.util.List;

public class CreateReservationRequest {

    private Long userId;
    private Long arrangementId;
    private Long arrangementTermId;
    private int numberOfPassengers;

    private PaymentType paymentType;
    private Integer numberOfInstallments;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;

    private boolean insuranceSelected;
    private Double expectedTotalPrice;
    private List<ReservationPassengerRequest> passengers;

    public CreateReservationRequest() {
    }

    public String getPassengerFirstName() {
        return passengerFirstName;
    }

    public void setPassengerFirstName(String passengerFirstName) {
        this.passengerFirstName = passengerFirstName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public void setPassengerLastName(String passengerLastName) {
        this.passengerLastName = passengerLastName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }
    public List<ReservationPassengerRequest> getPassengers() {
        return passengers;
    }
    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public boolean isInsuranceSelected() {
        return insuranceSelected;
    }

    public void setInsuranceSelected(boolean insuranceSelected) {
        this.insuranceSelected = insuranceSelected;
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

    public Double getExpectedTotalPrice() {
        return expectedTotalPrice;
    }

    public void setExpectedTotalPrice(Double expectedTotalPrice) {
        this.expectedTotalPrice = expectedTotalPrice;
    }
}