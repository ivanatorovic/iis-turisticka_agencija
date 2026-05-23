package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

@Entity
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransportType type;

    private String company;

    public Transport() {
    }

    public Long getId() {
        return id;
    }

    public TransportType getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}