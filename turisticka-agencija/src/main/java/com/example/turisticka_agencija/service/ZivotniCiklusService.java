package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.model.KorakZivotnogCiklusa;
import com.example.turisticka_agencija.repository.KorakZivotnogCiklusaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZivotniCiklusService {

    private final KorakZivotnogCiklusaRepository korakRepository;

    public ZivotniCiklusService(KorakZivotnogCiklusaRepository korakRepository) {
        this.korakRepository = korakRepository;
    }

    public KorakZivotnogCiklusa kreirajKorak(KorakZivotnogCiklusa korak) {
        validirajKorak(korak);
        korak.setAktivan(true);
        return korakRepository.save(korak);
    }

    public List<KorakZivotnogCiklusa> pronadjiSveKorake() {
        return korakRepository.findAll();
    }

    public List<KorakZivotnogCiklusa> pronadjiAktivneKorake() {
        return korakRepository.findByAktivanTrueOrderByRedosledAsc();
    }

    public KorakZivotnogCiklusa pronadjiPoId(Long id) {
        return korakRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Korak životnog ciklusa nije pronađen."));
    }

    public KorakZivotnogCiklusa izmeniKorak(Long id, KorakZivotnogCiklusa noviKorak) {
        KorakZivotnogCiklusa postojeci = pronadjiPoId(id);

        if (noviKorak.getRedosled() != null) {
            postojeci.setRedosled(noviKorak.getRedosled());
        }

        if (noviKorak.getStatus() != null) {
            postojeci.setStatus(noviKorak.getStatus());
        }

        if (noviKorak.getNaziv() != null) {
            postojeci.setNaziv(noviKorak.getNaziv());
        }

        if (noviKorak.getOpis() != null) {
            postojeci.setOpis(noviKorak.getOpis());
        }

        return korakRepository.save(postojeci);
    }

    public KorakZivotnogCiklusa deaktivirajKorak(Long id) {
        KorakZivotnogCiklusa korak = pronadjiPoId(id);
        korak.setAktivan(false);
        return korakRepository.save(korak);
    }

    public KorakZivotnogCiklusa aktivirajKorak(Long id) {
        KorakZivotnogCiklusa korak = pronadjiPoId(id);
        korak.setAktivan(true);
        return korakRepository.save(korak);
    }

    public void obrisiKorak(Long id) {
        korakRepository.deleteById(id);
    }

    private void validirajKorak(KorakZivotnogCiklusa korak) {
        if (korak.getRedosled() == null || korak.getRedosled() <= 0) {
            throw new RuntimeException("Redosled koraka mora biti pozitivan broj.");
        }

        if (korak.getStatus() == null) {
            throw new RuntimeException("Status koraka je obavezan.");
        }

        if (korak.getNaziv() == null || korak.getNaziv().isBlank()) {
            throw new RuntimeException("Naziv koraka je obavezan.");
        }
    }
}