package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfPassengers;
    private double totalPrice;
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Arrangement arrangement;

    @ManyToOne
    private ArrangementTerm arrangementTerm;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Integer numberOfInstallments;

    private Double installmentAmount;

    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;

    private double basePricePerPerson;
    private double dynamicPricePerPerson;

    private boolean insuranceSelected;
    private double insurancePrice;

    private double arrangementTotalPrice;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationPassenger> passengers = new ArrayList<>();

    public Reservation() {
        this.reservationDate = LocalDateTime.now();
        this.status = ReservationStatus.PENDING;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public List<ReservationPassenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<ReservationPassenger> passengers) {
        this.passengers = passengers;
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

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public double getBasePricePerPerson() {
        return basePricePerPerson;
    }

    public void setBasePricePerPerson(double basePricePerPerson) {
        this.basePricePerPerson = basePricePerPerson;
    }

    public double getDynamicPricePerPerson() {
        return dynamicPricePerPerson;
    }

    public void setDynamicPricePerPerson(double dynamicPricePerPerson) {
        this.dynamicPricePerPerson = dynamicPricePerPerson;
    }

    public boolean isInsuranceSelected() {
        return insuranceSelected;
    }

    public void setInsuranceSelected(boolean insuranceSelected) {
        this.insuranceSelected = insuranceSelected;
    }

    public double getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(double insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public double getArrangementTotalPrice() {
        return arrangementTotalPrice;
    }

    public void setArrangementTotalPrice(double arrangementTotalPrice) {
        this.arrangementTotalPrice = arrangementTotalPrice;
    }

    public Long getId() {
        return id;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public ArrangementTerm getArrangementTerm() {
        return arrangementTerm;
    }

    public void setArrangementTerm(ArrangementTerm arrangementTerm) {
        this.arrangementTerm = arrangementTerm;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }


}