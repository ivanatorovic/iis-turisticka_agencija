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
    @ManyToOne
    private Destination destination;
    private String description;
    private double basePrice;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "arrangement_terms",
            joinColumns = @JoinColumn(name = "arrangement_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private List<Term> terms = new ArrayList<>();

    private int numberOfNights;

    @ManyToOne
    private Accommodation accommodation;

    @ManyToOne
    private Transport transport;

    @ManyToMany
    @JoinTable(
            name = "arrangement_additional_services",
            joinColumns = @JoinColumn(name = "arrangement_id"),
            inverseJoinColumns = @JoinColumn(name = "additional_service_id")
    )
    private List<AdditionalService> additionalServices = new ArrayList<>();

    public Arrangement() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Destination getDestination() {
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

    public void setDestination(Destination destination) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public Transport getTransport() {
        return transport;
    }

    public List<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setAdditionalServices(List<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

}