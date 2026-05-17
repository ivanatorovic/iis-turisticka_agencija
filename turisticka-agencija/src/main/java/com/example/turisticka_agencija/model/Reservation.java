package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    public Reservation() {
        this.reservationDate = LocalDateTime.now();
        this.status = ReservationStatus.PENDING;
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


}