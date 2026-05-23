package com.example.turisticka_agencija.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ArrangementTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Arrangement arrangement;

    @ManyToOne
    private Term term;

    private int capacity;

    private int reservedSpots;

    public ArrangementTerm() {
    }

    public Long getId() {
        return id;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public Term getTerm() {
        return term;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getReservedSpots() {
        return reservedSpots;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setReservedSpots(int reservedSpots) {
        this.reservedSpots = reservedSpots;
    }

    public int getAvailableSpots() {
        return capacity - reservedSpots;
    }
}