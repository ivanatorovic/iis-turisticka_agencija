package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

@Entity
public class KorakZivotnogCiklusa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer redosled;

    @Enumerated(EnumType.STRING)
    private StatusZalbe status;

    private String naziv;
    private String opis;

    private boolean aktivan = true;

    public KorakZivotnogCiklusa() {
    }

    public Long getId() {
        return id;
    }

    public Integer getRedosled() {
        return redosled;
    }

    public void setRedosled(Integer redosled) {
        this.redosled = redosled;
    }

    public StatusZalbe getStatus() {
        return status;
    }

    public void setStatus(StatusZalbe status) {
        this.status = status;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }
}