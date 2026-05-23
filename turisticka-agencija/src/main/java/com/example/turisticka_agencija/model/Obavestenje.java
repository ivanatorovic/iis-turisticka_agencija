package com.example.turisticka_agencija.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Obavestenje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long zalbaId;
    private String primalacUloga;
    private Long primalacId;

    @Column(length = 1000)
    private String poruka;

    private boolean procitano;
    private LocalDateTime datumKreiranja;

    public Obavestenje() {
    }

    public Obavestenje(Long zalbaId, String primalacUloga, Long primalacId, String poruka) {
        this.zalbaId = zalbaId;
        this.primalacUloga = primalacUloga;
        this.primalacId = primalacId;
        this.poruka = poruka;
        this.procitano = false;
        this.datumKreiranja = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getZalbaId() {
        return zalbaId;
    }

    public void setZalbaId(Long zalbaId) {
        this.zalbaId = zalbaId;
    }

    public String getPrimalacUloga() {
        return primalacUloga;
    }

    public void setPrimalacUloga(String primalacUloga) {
        this.primalacUloga = primalacUloga;
    }

    public Long getPrimalacId() {
        return primalacId;
    }

    public void setPrimalacId(Long primalacId) {
        this.primalacId = primalacId;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public boolean isProcitano() {
        return procitano;
    }

    public void setProcitano(boolean procitano) {
        this.procitano = procitano;
    }

    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }
}