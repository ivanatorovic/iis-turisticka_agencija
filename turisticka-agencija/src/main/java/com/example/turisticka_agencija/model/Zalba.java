package com.example.turisticka_agencija.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Zalba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservationId;

    private String naslov;

    @Column(length = 1000)
    private String opis;

    @Enumerated(EnumType.STRING)
    private TipZalbe tipZalbe;

    @Enumerated(EnumType.STRING)
    private StatusZalbe status;

    private Long idTure;

    private Long putnikId;

    private boolean hitna;

    private String nazivTure;

    @Enumerated(EnumType.STRING)
    private TimZalbe dodeljeniTim;

    private LocalDateTime datumKreiranja;
    private LocalDateTime datumResavanja;
    private LocalDateTime datumZatvaranja;
    private LocalDateTime rokZaResavanje;

    private String dokumentacijaUrl;

    private boolean operaterObavesten;
    private boolean timObavesten;
    private boolean putnikObavesten;

    @Column(length = 1000)
    private String opisResenja;

    @Column(length = 1000)
    private String preduzeteMere;

    private String kontaktiranaStrana;
    private String alternativnoResenje;
    private String najduziKorak;

    @Column(length = 1000)
    private String napomenaTima;

    private Integer ocena;

    @Column(length = 1000)
    private String komentarOcene;

    public Zalba() {
    }

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = StatusZalbe.NOVO;
        }

        if (this.datumKreiranja == null) {
            this.datumKreiranja = LocalDateTime.now();
        }

        if (this.rokZaResavanje == null) {
            this.rokZaResavanje = this.datumKreiranja.plusDays(3);
        }
    }

    public Long getId() {
        return id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public TipZalbe getTipZalbe() {
        return tipZalbe;
    }

    public void setTipZalbe(TipZalbe tipZalbe) {
        this.tipZalbe = tipZalbe;
    }

    public StatusZalbe getStatus() {
        return status;
    }

    public void setStatus(StatusZalbe status) {
        this.status = status;
    }

    public Long getIdTure() {
        return idTure;
    }

    public void setIdTure(Long idTure) {
        this.idTure = idTure;
    }

    public Long getPutnikId() {
        return putnikId;
    }

    public void setPutnikId(Long putnikId) {
        this.putnikId = putnikId;
    }

    public boolean isHitna() {
        return hitna;
    }

    public void setHitna(boolean hitna) {
        this.hitna = hitna;
    }

    public TimZalbe getDodeljeniTim() {
        return dodeljeniTim;
    }

    public void setDodeljeniTim(TimZalbe dodeljeniTim) {
        this.dodeljeniTim = dodeljeniTim;
    }

    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public LocalDateTime getDatumResavanja() {
        return datumResavanja;
    }

    public void setDatumResavanja(LocalDateTime datumResavanja) {
        this.datumResavanja = datumResavanja;
    }

    public LocalDateTime getDatumZatvaranja() {
        return datumZatvaranja;
    }

    public void setDatumZatvaranja(LocalDateTime datumZatvaranja) {
        this.datumZatvaranja = datumZatvaranja;
    }

    public LocalDateTime getRokZaResavanje() {
        return rokZaResavanje;
    }

    public void setRokZaResavanje(LocalDateTime rokZaResavanje) {
        this.rokZaResavanje = rokZaResavanje;
    }

    public String getDokumentacijaUrl() {
        return dokumentacijaUrl;
    }

    public void setDokumentacijaUrl(String dokumentacijaUrl) {
        this.dokumentacijaUrl = dokumentacijaUrl;
    }

    public boolean isOperaterObavesten() {
        return operaterObavesten;
    }

    public void setOperaterObavesten(boolean operaterObavesten) {
        this.operaterObavesten = operaterObavesten;
    }

    public boolean isTimObavesten() {
        return timObavesten;
    }

    public void setTimObavesten(boolean timObavesten) {
        this.timObavesten = timObavesten;
    }

    public boolean isPutnikObavesten() {
        return putnikObavesten;
    }

    public void setPutnikObavesten(boolean putnikObavesten) {
        this.putnikObavesten = putnikObavesten;
    }

    public String getOpisResenja() {
        return opisResenja;
    }

    public void setOpisResenja(String opisResenja) {
        this.opisResenja = opisResenja;
    }

    public String getPreduzeteMere() {
        return preduzeteMere;
    }

    public void setPreduzeteMere(String preduzeteMere) {
        this.preduzeteMere = preduzeteMere;
    }

    public String getKontaktiranaStrana() {
        return kontaktiranaStrana;
    }

    public void setKontaktiranaStrana(String kontaktiranaStrana) {
        this.kontaktiranaStrana = kontaktiranaStrana;
    }

    public String getAlternativnoResenje() {
        return alternativnoResenje;
    }

    public void setAlternativnoResenje(String alternativnoResenje) {
        this.alternativnoResenje = alternativnoResenje;
    }

    public String getNajduziKorak() {
        return najduziKorak;
    }

    public void setNajduziKorak(String najduziKorak) {
        this.najduziKorak = najduziKorak;
    }

    public String getNapomenaTima() {
        return napomenaTima;
    }

    public void setNapomenaTima(String napomenaTima) {
        this.napomenaTima = napomenaTima;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public String getKomentarOcene() {
        return komentarOcene;
    }

    public void setKomentarOcene(String komentarOcene) {
        this.komentarOcene = komentarOcene;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }


    public String getNazivTure() {
        return nazivTure;
    }

    public void setNazivTure(String nazivTure) {
        this.nazivTure = nazivTure;
    }
}