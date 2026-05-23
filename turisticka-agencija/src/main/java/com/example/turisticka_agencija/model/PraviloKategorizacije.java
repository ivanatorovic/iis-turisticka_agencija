package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

@Entity
public class PraviloKategorizacije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipZalbe tipZalbe;

    @Enumerated(EnumType.STRING)
    private TimZalbe timZalbe;

    private boolean hitnoPoDefaultu;
    private Integer rokDana;

    public PraviloKategorizacije() {
    }

    public Long getId() {
        return id;
    }

    public TipZalbe getTipZalbe() {
        return tipZalbe;
    }

    public void setTipZalbe(TipZalbe tipZalbe) {
        this.tipZalbe = tipZalbe;
    }

    public TimZalbe getTimZalbe() {
        return timZalbe;
    }

    public void setTimZalbe(TimZalbe timZalbe) {
        this.timZalbe = timZalbe;
    }

    public boolean isHitnoPoDefaultu() {
        return hitnoPoDefaultu;
    }

    public void setHitnoPoDefaultu(boolean hitnoPoDefaultu) {
        this.hitnoPoDefaultu = hitnoPoDefaultu;
    }

    public Integer getRokDana() {
        return rokDana;
    }

    public void setRokDana(Integer rokDana) {
        this.rokDana = rokDana;
    }
}