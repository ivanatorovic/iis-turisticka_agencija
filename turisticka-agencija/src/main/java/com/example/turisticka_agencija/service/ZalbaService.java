package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.ObavestenjeRepository;
import com.example.turisticka_agencija.repository.PraviloKategorizacijeRepository;
import com.example.turisticka_agencija.repository.ZalbaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZalbaService {

    private final ZalbaRepository zalbaRepository;
    private final ObavestenjeRepository obavestenjeRepository;
    private final PraviloKategorizacijeRepository praviloRepository;

    public ZalbaService(
            ZalbaRepository zalbaRepository,
            ObavestenjeRepository obavestenjeRepository,
            PraviloKategorizacijeRepository praviloRepository
    ) {
        this.zalbaRepository = zalbaRepository;
        this.obavestenjeRepository = obavestenjeRepository;
        this.praviloRepository = praviloRepository;
    }

    public Zalba kreirajZalbu(Zalba zalba) {
        validirajZalbu(zalba);

        zalba.setStatus(StatusZalbe.NOVO);
        zalba.setOperaterObavesten(true);

        Zalba sacuvana = zalbaRepository.save(zalba);

        kreirajObavestenje(
                sacuvana.getId(),
                "OPERATER",
                null,
                "Pristigla je nova žalba: " + sacuvana.getNaslov()
        );

        return sacuvana;
    }

    public List<Zalba> pronadjiSve() {
        return zalbaRepository.findAll();
    }

    public Zalba pronadjiPoId(Long id) {
        return zalbaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Žalba nije pronađena."));
    }

    public List<Zalba> pronadjiPoPutniku(Long putnikId) {
        return zalbaRepository.findByPutnikId(putnikId);
    }

    public List<Zalba> pronadjiNoveZalbe() {
        return zalbaRepository.findByStatus(StatusZalbe.NOVO);
    }

    public List<Zalba> pronadjiPoStatusu(StatusZalbe status) {
        return zalbaRepository.findByStatus(status);
    }

    public List<Zalba> pronadjiPoTimu(TimZalbe timZalbe) {
        return zalbaRepository.findByDodeljeniTim(timZalbe);
    }

    public List<Zalba> pronadjiHitne() {
        return zalbaRepository.findByHitnaTrue();
    }

    public Zalba izmeniZalbu(Long id, Zalba izmenjenaZalba) {
        Zalba postojecaZalba = pronadjiPoId(id);

        if (postojecaZalba.getStatus() != StatusZalbe.NOVO) {
            throw new RuntimeException("Žalba može da se menja samo dok je u statusu NOVO.");
        }

        if (izmenjenaZalba.getNaslov() == null || izmenjenaZalba.getNaslov().isBlank()) {
            throw new RuntimeException("Naslov žalbe je obavezan.");
        }

        if (izmenjenaZalba.getOpis() == null || izmenjenaZalba.getOpis().isBlank()) {
            throw new RuntimeException("Opis žalbe je obavezan.");
        }

        postojecaZalba.setNaslov(izmenjenaZalba.getNaslov());
        postojecaZalba.setOpis(izmenjenaZalba.getOpis());
        postojecaZalba.setTipZalbe(izmenjenaZalba.getTipZalbe());
        postojecaZalba.setIdTure(izmenjenaZalba.getIdTure());
        postojecaZalba.setDokumentacijaUrl(izmenjenaZalba.getDokumentacijaUrl());

        return zalbaRepository.save(postojecaZalba);
    }

    public void obrisiZalbu(Long id) {
        Zalba zalba = pronadjiPoId(id);

        if (zalba.getStatus() != StatusZalbe.NOVO) {
            throw new RuntimeException("Žalba može da se obriše samo dok je u statusu NOVO.");
        }

        zalbaRepository.delete(zalba);
    }

    public Zalba dodajDokumentaciju(Long id, MultipartFile file) {
        Zalba zalba = pronadjiPoId(id);

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Fajl nije poslat.");
        }

        try {
            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.isBlank()) {
                throw new RuntimeException("Naziv fajla nije validan.");
            }

            Path uploadFolder = Path.of("uploads", "zalbe", String.valueOf(id));

            if (!Files.exists(uploadFolder)) {
                Files.createDirectories(uploadFolder);
            }

            Path filePath = uploadFolder.resolve(originalFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            zalba.setDokumentacijaUrl(filePath.toString());

            return zalbaRepository.save(zalba);

        } catch (IOException e) {
            throw new RuntimeException("Greška prilikom čuvanja dokumentacije.");
        }
    }

    public Zalba kategorizujZalbu(Long id, TipZalbe tipZalbe, boolean hitna) {
        Zalba zalba = pronadjiPoId(id);

        if (zalba.getStatus() != StatusZalbe.NOVO) {
            throw new RuntimeException("Kategorizacija je dozvoljena samo za nove žalbe.");
        }

        zalba.setTipZalbe(tipZalbe);
        zalba.setHitna(hitna);

        return zalbaRepository.save(zalba);
    }

    public Zalba dodeliTimu(Long id, TimZalbe timZalbe, boolean hitna) {
        Zalba zalba = pronadjiPoId(id);

        zalba.setDodeljeniTim(timZalbe);
        zalba.setHitna(hitna);
        zalba.setStatus(StatusZalbe.U_OBRADI);
        zalba.setTimObavesten(true);
        zalba.setPutnikObavesten(true);

        Zalba sacuvana = zalbaRepository.save(zalba);

        kreirajObavestenje(
                sacuvana.getId(),
                timZalbe.name(),
                null,
                "Dodeljena vam je nova žalba: " + sacuvana.getNaslov()
        );

        kreirajObavestenje(
                sacuvana.getId(),
                "PUTNIK",
                sacuvana.getPutnikId(),
                "Vaša žalba je prešla u status U_OBRADI."
        );

        return sacuvana;
    }

    public Zalba automatskiDodeliPoPravilu(Long id) {
        Zalba zalba = pronadjiPoId(id);

        PraviloKategorizacije pravilo = praviloRepository.findByTipZalbe(zalba.getTipZalbe())
                .orElseThrow(() -> new RuntimeException("Ne postoji pravilo za ovaj tip žalbe."));

        zalba.setHitna(pravilo.isHitnoPoDefaultu());

        if (pravilo.getRokDana() != null) {
            zalba.setRokZaResavanje(LocalDateTime.now().plusDays(pravilo.getRokDana()));
        }

        return dodeliTimu(id, pravilo.getTimZalbe(), pravilo.isHitnoPoDefaultu());
    }

    public Zalba oznaciDaCekaOdgovor(Long id) {
        Zalba zalba = pronadjiPoId(id);

        if (zalba.getStatus() != StatusZalbe.U_OBRADI) {
            throw new RuntimeException("Status ČEKA ODGOVOR može da se postavi samo kada je žalba u obradi.");
        }

        zalba.setStatus(StatusZalbe.CEKA_ODGOVOR);
        zalba.setPutnikObavesten(true);

        Zalba sacuvana = zalbaRepository.save(zalba);

        kreirajObavestenje(
                sacuvana.getId(),
                "PUTNIK",
                sacuvana.getPutnikId(),
                "Vaša žalba je prešla u status ČEKA_ODGOVOR."
        );

        return sacuvana;
    }

    public Zalba evidentirajAkcijuTima(
            Long id,
            String preduzeteMere,
            String kontaktiranaStrana,
            String alternativnoResenje,
            String najduziKorak,
            String napomenaTima
    ) {
        Zalba zalba = pronadjiPoId(id);

        if (zalba.getStatus() != StatusZalbe.U_OBRADI && zalba.getStatus() != StatusZalbe.CEKA_ODGOVOR) {
            throw new RuntimeException("Akcija tima može da se evidentira samo za žalbu koja je u obradi ili čeka odgovor.");
        }

        zalba.setPreduzeteMere(preduzeteMere);
        zalba.setKontaktiranaStrana(kontaktiranaStrana);
        zalba.setAlternativnoResenje(alternativnoResenje);
        zalba.setNajduziKorak(najduziKorak);
        zalba.setNapomenaTima(napomenaTima);

        return zalbaRepository.save(zalba);
    }

    public Zalba unesiResenje(
            Long id,
            String opisResenja,
            String preduzeteMere,
            String kontaktiranaStrana,
            String alternativnoResenje,
            String najduziKorak
    ) {
        Zalba zalba = pronadjiPoId(id);

        if (zalba.getStatus() != StatusZalbe.U_OBRADI && zalba.getStatus() != StatusZalbe.CEKA_ODGOVOR) {
            throw new RuntimeException("Rešenje može da se unese samo za žalbu koja je u obradi ili čeka odgovor.");
        }

        zalba.setOpisResenja(opisResenja);
        zalba.setPreduzeteMere(preduzeteMere);
        zalba.setKontaktiranaStrana(kontaktiranaStrana);
        zalba.setAlternativnoResenje(alternativnoResenje);
        zalba.setNajduziKorak(najduziKorak);
        zalba.setStatus(StatusZalbe.RESENO);
        zalba.setDatumResavanja(LocalDateTime.now());
        zalba.setPutnikObavesten(true);

        Zalba sacuvana = zalbaRepository.save(zalba);

        kreirajObavestenje(
                sacuvana.getId(),
                "PUTNIK",
                sacuvana.getPutnikId(),
                "Vaša žalba je rešena. Možete pogledati ishod rešavanja."
        );

        return sacuvana;
    }

    public Zalba zatvoriZalbu(Long id) {
        Zalba zalba = pronadjiPoId(id);

        if (zalba.getStatus() != StatusZalbe.RESENO) {
            throw new RuntimeException("Žalba može da se zatvori samo ako je prethodno rešena.");
        }

        zalba.setStatus(StatusZalbe.ZATVORENO);
        zalba.setDatumZatvaranja(LocalDateTime.now());
        zalba.setPutnikObavesten(true);

        Zalba sacuvana = zalbaRepository.save(zalba);

        kreirajObavestenje(
                sacuvana.getId(),
                "PUTNIK",
                sacuvana.getPutnikId(),
                "Vaša žalba je zatvorena. Možete oceniti rešavanje."
        );

        return sacuvana;
    }

    public Zalba oceniZalbu(Long id, Integer ocena, String komentarOcene) {
        Zalba zalba = pronadjiPoId(id);

        if (zalba.getStatus() != StatusZalbe.ZATVORENO) {
            throw new RuntimeException("Žalba može da se oceni samo kada je zatvorena.");
        }

        if (ocena == null || ocena < 1 || ocena > 5) {
            throw new RuntimeException("Ocena mora biti između 1 i 5.");
        }

        zalba.setOcena(ocena);
        zalba.setKomentarOcene(komentarOcene);

        return zalbaRepository.save(zalba);
    }

    public List<Obavestenje> obavestenjaZaUlogu(String uloga) {
        return obavestenjeRepository.findByPrimalacUloga(uloga);
    }

    public List<Obavestenje> obavestenjaZaKorisnika(String uloga, Long korisnikId) {
        return obavestenjeRepository.findByPrimalacUlogaAndPrimalacId(uloga, korisnikId);
    }

    public List<Obavestenje> obavestenjaZaZalbu(Long zalbaId) {
        return obavestenjeRepository.findByZalbaId(zalbaId);
    }

    public Map<String, Long> brojZalbiPoStatusu() {
        Map<String, Long> rezultat = new HashMap<>();

        for (StatusZalbe status : StatusZalbe.values()) {
            rezultat.put(status.name(), zalbaRepository.countByStatus(status));
        }

        return rezultat;
    }

    public Map<String, Long> brojZalbiPoTimu() {
        Map<String, Long> rezultat = new HashMap<>();

        for (TimZalbe tim : TimZalbe.values()) {
            rezultat.put(tim.name(), zalbaRepository.countByDodeljeniTim(tim));
        }

        return rezultat;
    }

    public Map<String, Long> brojZalbiPoTipu() {
        Map<String, Long> rezultat = new HashMap<>();

        for (TipZalbe tip : TipZalbe.values()) {
            rezultat.put(tip.name(), zalbaRepository.countByTipZalbe(tip));
        }

        return rezultat;
    }

    public Double prosecnaOcena() {
        List<Zalba> zalbe = zalbaRepository.findAll();

        return zalbe.stream()
                .filter(zalba -> zalba.getOcena() != null)
                .mapToInt(Zalba::getOcena)
                .average()
                .orElse(0.0);
    }

    public Double prosecnoVremeResavanjaUSatima() {
        List<Zalba> zalbe = zalbaRepository.findAll();

        return zalbe.stream()
                .filter(z -> z.getDatumKreiranja() != null && z.getDatumResavanja() != null)
                .mapToLong(z -> Duration.between(z.getDatumKreiranja(), z.getDatumResavanja()).toHours())
                .average()
                .orElse(0.0);
    }

    public List<Zalba> zalbeKojeKasne() {
        LocalDateTime sada = LocalDateTime.now();

        return zalbaRepository.findAll()
                .stream()
                .filter(z -> z.getRokZaResavanje() != null)
                .filter(z -> z.getStatus() != StatusZalbe.RESENO && z.getStatus() != StatusZalbe.ZATVORENO)
                .filter(z -> z.getRokZaResavanje().isBefore(sada))
                .toList();
    }

    public Map<String, Long> najproblematicnijiKoraci() {
        Map<String, Long> rezultat = new HashMap<>();

        for (Zalba zalba : zalbaRepository.findAll()) {
            if (zalba.getNajduziKorak() != null && !zalba.getNajduziKorak().isBlank()) {
                rezultat.put(
                        zalba.getNajduziKorak(),
                        rezultat.getOrDefault(zalba.getNajduziKorak(), 0L) + 1
                );
            }
        }

        return rezultat;
    }

    private void kreirajObavestenje(Long zalbaId, String primalacUloga, Long primalacId, String poruka) {
        Obavestenje obavestenje = new Obavestenje(zalbaId, primalacUloga, primalacId, poruka);
        obavestenjeRepository.save(obavestenje);
    }

    private void validirajZalbu(Zalba zalba) {
        if (zalba.getNaslov() == null || zalba.getNaslov().isBlank()) {
            throw new RuntimeException("Naslov žalbe je obavezan.");
        }

        if (zalba.getOpis() == null || zalba.getOpis().isBlank()) {
            throw new RuntimeException("Opis žalbe je obavezan.");
        }

        if (zalba.getTipZalbe() == null) {
            throw new RuntimeException("Tip žalbe je obavezan.");
        }

        if (zalba.getIdTure() == null) {
            throw new RuntimeException("Identifikator ture je obavezan.");
        }

        if (zalba.getPutnikId() == null) {
            throw new RuntimeException("Putnik je obavezan.");
        }
    }
}