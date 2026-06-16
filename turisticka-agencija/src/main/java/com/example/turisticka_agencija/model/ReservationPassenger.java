package com.example.turisticka_agencija.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ReservationPassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int age;

    private double price;
    private String discountDescription;

    @ManyToOne
    @JsonIgnore
    private Reservation reservation;

    public ReservationPassenger() {}

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public double getPrice() {
        return price;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}