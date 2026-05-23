package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.Korisnik;
import com.example.turisticka_agencija.model.UlogaKorisnika;
import com.example.turisticka_agencija.service.KorisnikService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/korisnici")
@CrossOrigin(origins = "*")
public class KorisnikController {

    private final KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @PostMapping
    public Korisnik kreirajKorisnika(@RequestBody Korisnik korisnik) {
        return korisnikService.kreirajKorisnika(korisnik);
    }

    @GetMapping
    public List<Korisnik> pronadjiSve() {
        return korisnikService.pronadjiSve();
    }

    @GetMapping("/aktivni")
    public List<Korisnik> pronadjiAktivne() {
        return korisnikService.pronadjiAktivne();
    }

    @GetMapping("/{id}")
    public Korisnik pronadjiPoId(@PathVariable Long id) {
        return korisnikService.pronadjiPoId(id);
    }

    @GetMapping("/uloga/{uloga}")
    public List<Korisnik> pronadjiPoUlozi(@PathVariable UlogaKorisnika uloga) {
        return korisnikService.pronadjiPoUlozi(uloga);
    }

    @PutMapping("/{id}")
    public Korisnik izmeniKorisnika(
            @PathVariable Long id,
            @RequestBody Korisnik korisnik
    ) {
        return korisnikService.izmeniKorisnika(id, korisnik);
    }

    @PutMapping("/{id}/deaktiviraj")
    public Korisnik deaktivirajKorisnika(@PathVariable Long id) {
        return korisnikService.deaktivirajKorisnika(id);
    }

    @PutMapping("/{id}/aktiviraj")
    public Korisnik aktivirajKorisnika(@PathVariable Long id) {
        return korisnikService.aktivirajKorisnika(id);
    }

    @DeleteMapping("/{id}")
    public void obrisiKorisnika(@PathVariable Long id) {
        korisnikService.obrisiKorisnika(id);
    }
}