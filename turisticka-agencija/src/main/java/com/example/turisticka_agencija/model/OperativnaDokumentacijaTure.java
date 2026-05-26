package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

@Entity
@Table(name = "operativna_dokumentacija_ture")
public class OperativnaDokumentacijaTure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long arrangementId;

    private String kontaktSmestaja;

    private String emailSmestaja;

    private String odgovornaOsobaSmestaja;

    private String kontaktPrevoznika;

    private String emailPrevoznika;

    private String odgovornaOsobaPrevoznika;

    private String kontaktHitno;

    @Column(length = 3000)
    private String planPuta;

    @Column(length = 3000)
    private String napomeneZaTim;

    public OperativnaDokumentacijaTure() {
    }

    public OperativnaDokumentacijaTure(
            Long arrangementId,
            String kontaktSmestaja,
            String emailSmestaja,
            String odgovornaOsobaSmestaja,
            String kontaktPrevoznika,
            String emailPrevoznika,
            String odgovornaOsobaPrevoznika,
            String kontaktHitno,
            String planPuta,
            String napomeneZaTim
    ) {
        this.arrangementId = arrangementId;
        this.kontaktSmestaja = kontaktSmestaja;
        this.emailSmestaja = emailSmestaja;
        this.odgovornaOsobaSmestaja = odgovornaOsobaSmestaja;
        this.kontaktPrevoznika = kontaktPrevoznika;
        this.emailPrevoznika = emailPrevoznika;
        this.odgovornaOsobaPrevoznika = odgovornaOsobaPrevoznika;
        this.kontaktHitno = kontaktHitno;
        this.planPuta = planPuta;
        this.napomeneZaTim = napomeneZaTim;
    }

    public Long getId() {
        return id;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public String getKontaktSmestaja() {
        return kontaktSmestaja;
    }

    public void setKontaktSmestaja(String kontaktSmestaja) {
        this.kontaktSmestaja = kontaktSmestaja;
    }

    public String getEmailSmestaja() {
        return emailSmestaja;
    }

    public void setEmailSmestaja(String emailSmestaja) {
        this.emailSmestaja = emailSmestaja;
    }

    public String getOdgovornaOsobaSmestaja() {
        return odgovornaOsobaSmestaja;
    }

    public void setOdgovornaOsobaSmestaja(String odgovornaOsobaSmestaja) {
        this.odgovornaOsobaSmestaja = odgovornaOsobaSmestaja;
    }

    public String getKontaktPrevoznika() {
        return kontaktPrevoznika;
    }

    public void setKontaktPrevoznika(String kontaktPrevoznika) {
        this.kontaktPrevoznika = kontaktPrevoznika;
    }

    public String getEmailPrevoznika() {
        return emailPrevoznika;
    }

    public void setEmailPrevoznika(String emailPrevoznika) {
        this.emailPrevoznika = emailPrevoznika;
    }

    public String getOdgovornaOsobaPrevoznika() {
        return odgovornaOsobaPrevoznika;
    }

    public void setOdgovornaOsobaPrevoznika(String odgovornaOsobaPrevoznika) {
        this.odgovornaOsobaPrevoznika = odgovornaOsobaPrevoznika;
    }

    public String getKontaktHitno() {
        return kontaktHitno;
    }

    public void setKontaktHitno(String kontaktHitno) {
        this.kontaktHitno = kontaktHitno;
    }

    public String getPlanPuta() {
        return planPuta;
    }

    public void setPlanPuta(String planPuta) {
        this.planPuta = planPuta;
    }

    public String getNapomeneZaTim() {
        return napomeneZaTim;
    }

    public void setNapomeneZaTim(String napomeneZaTim) {
        this.napomeneZaTim = napomeneZaTim;
    }
}