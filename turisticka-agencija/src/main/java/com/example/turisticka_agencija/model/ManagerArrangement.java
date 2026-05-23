package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

@Entity
public class ManagerArrangement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double basePrice;
    private int numberOfNights;
    private String imageUrl;

    @ManyToOne
    private Workflow workflow;

    @ManyToOne
    private User manager;

    @ManyToOne
    private Destination destination;

    @ManyToOne
    private Accommodation accommodation;

    @ManyToOne
    private Transport transport;

    @Enumerated(EnumType.STRING)
    private ManagerArrangementStatus status = ManagerArrangementStatus.DRAFT;

    public ManagerArrangement() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public User getManager() {
        return manager;
    }

    public Destination getDestination() {
        return destination;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public Transport getTransport() {
        return transport;
    }

    public ManagerArrangementStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setStatus(ManagerArrangementStatus status) {
        this.status = status;
    }
}