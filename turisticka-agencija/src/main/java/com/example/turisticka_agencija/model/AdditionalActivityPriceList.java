package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AdditionalActivityPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AdditionalActivityExecution additionalActivityExecution;

    private double price;

    private LocalDate validFrom;

    private LocalDate validTo;

    public AdditionalActivityPriceList() {
    }

    public Long getId() {
        return id;
    }

    public AdditionalActivityExecution getAdditionalActivityExecution() {
        return additionalActivityExecution;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setAdditionalActivityExecution(AdditionalActivityExecution additionalActivityExecution) {
        this.additionalActivityExecution = additionalActivityExecution;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}