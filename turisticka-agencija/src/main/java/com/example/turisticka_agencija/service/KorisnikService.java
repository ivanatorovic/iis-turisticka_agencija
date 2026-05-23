package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.model.Korisnik;
import com.example.turisticka_agencija.model.UlogaKorisnika;
import com.example.turisticka_agencija.repository.KorisnikRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public KorisnikService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    public Korisnik kreirajKorisnika(Korisnik korisnik) {
        validirajKorisnika(korisnik);

        korisnikRepository.findByEmail(korisnik.getEmail()).ifPresent(k -> {
            throw new RuntimeException("Korisnik sa ovim email-om već postoji.");
        });

        korisnik.setAktivan(true);

        return korisnikRepository.save(korisnik);
    }

    public List<Korisnik> pronadjiSve() {
        return korisnikRepository.findAll();
    }

    public List<Korisnik> pronadjiAktivne() {
        return korisnikRepository.findByAktivanTrue();
    }

    public Korisnik pronadjiPoId(Long id) {
        return korisnikRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen."));
    }

    public List<Korisnik> pronadjiPoUlozi(UlogaKorisnika uloga) {
        return korisnikRepository.findByUloga(uloga);
    }

    public Korisnik izmeniKorisnika(Long id, Korisnik noviKorisnik) {
        Korisnik postojeci = pronadjiPoId(id);

        if (noviKorisnik.getIme() != null) {
            postojeci.setIme(noviKorisnik.getIme());
        }

        if (noviKorisnik.getPrezime() != null) {
            postojeci.setPrezime(noviKorisnik.getPrezime());
        }

        if (noviKorisnik.getEmail() != null) {
            postojeci.setEmail(noviKorisnik.getEmail());
        }

        if (noviKorisnik.getLozinka() != null) {
            postojeci.setLozinka(noviKorisnik.getLozinka());
        }

        if (noviKorisnik.getUloga() != null) {
            postojeci.setUloga(noviKorisnik.getUloga());
        }

        return korisnikRepository.save(postojeci);
    }

    public Korisnik deaktivirajKorisnika(Long id) {
        Korisnik korisnik = pronadjiPoId(id);
        korisnik.setAktivan(false);
        return korisnikRepository.save(korisnik);
    }

    public Korisnik aktivirajKorisnika(Long id) {
        Korisnik korisnik = pronadjiPoId(id);
        korisnik.setAktivan(true);
        return korisnikRepository.save(korisnik);
    }

    public void obrisiKorisnika(Long id) {
        korisnikRepository.deleteById(id);
    }

    private void validirajKorisnika(Korisnik korisnik) {
        if (korisnik.getIme() == null || korisnik.getIme().isBlank()) {
            throw new RuntimeException("Ime korisnika je obavezno.");
        }

        if (korisnik.getPrezime() == null || korisnik.getPrezime().isBlank()) {
            throw new RuntimeException("Prezime korisnika je obavezno.");
        }

        if (korisnik.getEmail() == null || korisnik.getEmail().isBlank()) {
            throw new RuntimeException("Email korisnika je obavezan.");
        }

        if (korisnik.getLozinka() == null || korisnik.getLozinka().isBlank()) {
            throw new RuntimeException("Lozinka korisnika je obavezna.");
        }

        if (korisnik.getUloga() == null) {
            throw new RuntimeException("Uloga korisnika je obavezna.");
        }
    }
}