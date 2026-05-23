package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.KorakZivotnogCiklusa;
import com.example.turisticka_agencija.service.ZivotniCiklusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zivotni-ciklus")
@CrossOrigin(origins = "*")
public class ZivotniCiklusController {

    private final ZivotniCiklusService zivotniCiklusService;

    public ZivotniCiklusController(ZivotniCiklusService zivotniCiklusService) {
        this.zivotniCiklusService = zivotniCiklusService;
    }

    @PostMapping("/koraci")
    public KorakZivotnogCiklusa kreirajKorak(@RequestBody KorakZivotnogCiklusa korak) {
        return zivotniCiklusService.kreirajKorak(korak);
    }

    @GetMapping("/koraci")
    public List<KorakZivotnogCiklusa> pronadjiSveKorake() {
        return zivotniCiklusService.pronadjiSveKorake();
    }

    @GetMapping("/koraci/aktivni")
    public List<KorakZivotnogCiklusa> pronadjiAktivneKorake() {
        return zivotniCiklusService.pronadjiAktivneKorake();
    }

    @GetMapping("/koraci/{id}")
    public KorakZivotnogCiklusa pronadjiPoId(@PathVariable Long id) {
        return zivotniCiklusService.pronadjiPoId(id);
    }

    @PutMapping("/koraci/{id}")
    public KorakZivotnogCiklusa izmeniKorak(
            @PathVariable Long id,
            @RequestBody KorakZivotnogCiklusa korak
    ) {
        return zivotniCiklusService.izmeniKorak(id, korak);
    }

    @PutMapping("/koraci/{id}/deaktiviraj")
    public KorakZivotnogCiklusa deaktivirajKorak(@PathVariable Long id) {
        return zivotniCiklusService.deaktivirajKorak(id);
    }

    @PutMapping("/koraci/{id}/aktiviraj")
    public KorakZivotnogCiklusa aktivirajKorak(@PathVariable Long id) {
        return zivotniCiklusService.aktivirajKorak(id);
    }

    @DeleteMapping("/koraci/{id}")
    public void obrisiKorak(@PathVariable Long id) {
        zivotniCiklusService.obrisiKorak(id);
    }
}