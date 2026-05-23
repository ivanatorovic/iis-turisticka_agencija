package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.model.PraviloKategorizacije;
import com.example.turisticka_agencija.repository.PraviloKategorizacijeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PraviloKategorizacijeService {

    private final PraviloKategorizacijeRepository praviloRepository;

    public PraviloKategorizacijeService(PraviloKategorizacijeRepository praviloRepository) {
        this.praviloRepository = praviloRepository;
    }

    public PraviloKategorizacije kreirajPravilo(PraviloKategorizacije pravilo) {
        if (pravilo.getTipZalbe() == null) {
            throw new RuntimeException("Tip žalbe je obavezan.");
        }

        if (pravilo.getTimZalbe() == null) {
            throw new RuntimeException("Tim za žalbu je obavezan.");
        }

        if (pravilo.getRokDana() == null || pravilo.getRokDana() <= 0) {
            pravilo.setRokDana(3);
        }

        return praviloRepository.save(pravilo);
    }

    public List<PraviloKategorizacije> pronadjiSvaPravila() {
        return praviloRepository.findAll();
    }

    public PraviloKategorizacije izmeniPravilo(Long id, PraviloKategorizacije novoPravilo) {
        PraviloKategorizacije postojece = praviloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pravilo nije pronađeno."));

        postojece.setTipZalbe(novoPravilo.getTipZalbe());
        postojece.setTimZalbe(novoPravilo.getTimZalbe());
        postojece.setHitnoPoDefaultu(novoPravilo.isHitnoPoDefaultu());
        postojece.setRokDana(novoPravilo.getRokDana());

        return praviloRepository.save(postojece);
    }

    public void obrisiPravilo(Long id) {
        praviloRepository.deleteById(id);
    }
}