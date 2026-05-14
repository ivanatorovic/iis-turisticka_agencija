package com.example.turisticka_agencija.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Arrangement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String destination;
    private String description;
    private double basePrice;

    @ManyToMany
    @JoinTable(
            name = "arrangement_terms",
            joinColumns = @JoinColumn(name = "arrangement_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private List<Term> terms = new ArrayList<>();

    public Arrangement() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
}