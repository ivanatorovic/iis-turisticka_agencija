package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.DokumentacijaZalbeResponse;
import com.example.turisticka_agencija.model.Arrangement;
import com.example.turisticka_agencija.model.OperativnaDokumentacijaTure;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.model.Zalba;
import com.example.turisticka_agencija.repository.ArrangementRepository;
import com.example.turisticka_agencija.repository.OperativnaDokumentacijaTureRepository;
import com.example.turisticka_agencija.repository.UserRepository;
import com.example.turisticka_agencija.repository.ZalbaRepository;
import org.springframework.stereotype.Service;

@Service
public class DokumentacijaZalbeService {

    private final ZalbaRepository zalbaRepository;
    private final ArrangementRepository arrangementRepository;
    private final UserRepository userRepository;
    private final OperativnaDokumentacijaTureRepository dokumentacijaRepository;

    public DokumentacijaZalbeService(
            ZalbaRepository zalbaRepository,
            ArrangementRepository arrangementRepository,
            UserRepository userRepository,
            OperativnaDokumentacijaTureRepository dokumentacijaRepository
    ) {
        this.zalbaRepository = zalbaRepository;
        this.arrangementRepository = arrangementRepository;
        this.userRepository = userRepository;
        this.dokumentacijaRepository = dokumentacijaRepository;
    }

    public DokumentacijaZalbeResponse getDokumentacijaZaZalbu(Long zalbaId) {
        Zalba zalba = zalbaRepository.findById(zalbaId)
                .orElseThrow(() -> new RuntimeException("Žalba nije pronađena."));

        DokumentacijaZalbeResponse response = new DokumentacijaZalbeResponse();

        response.setZalbaId(zalba.getId());
        response.setNaslovZalbe(zalba.getNaslov());
        response.setOpisZalbe(zalba.getOpis());
        response.setTipZalbe(zalba.getTipZalbe() != null ? zalba.getTipZalbe().toString() : "-");
        response.setStatusZalbe(zalba.getStatus() != null ? zalba.getStatus().toString() : "-");
        response.setHitna(zalba.isHitna());

        Arrangement arrangement = null;

        if (zalba.getIdTure() != null) {
            arrangement = arrangementRepository.findById(zalba.getIdTure()).orElse(null);
        }

        if (arrangement != null) {
            response.setArrangementId(arrangement.getId());
            response.setNazivAranzmana(arrangement.getName());
            response.setOpisAranzmana(arrangement.getDescription());
            response.setOsnovnaCena(arrangement.getBasePrice());
            response.setBrojNocenja(arrangement.getNumberOfNights());

            if (arrangement.getDestination() != null) {
                response.setDestinacijaNaziv(arrangement.getDestination().getName());
                response.setDestinacijaDrzava(arrangement.getDestination().getCountry());
            }

            if (arrangement.getAccommodation() != null) {
                response.setNazivSmestaja(arrangement.getAccommodation().getName());

                if (arrangement.getAccommodation().getCategory() != null) {
                    response.setKategorijaSmestaja(arrangement.getAccommodation().getCategory().toString());
                }
            }

            if (arrangement.getTransport() != null) {
                if (arrangement.getTransport().getType() != null) {
                    response.setTipPrevoza(arrangement.getTransport().getType().toString());
                }

                response.setKompanijaPrevoza(arrangement.getTransport().getCompany());
            }
        } else {
            response.setArrangementId(zalba.getIdTure());
            response.setNazivAranzmana("Aranžman nije pronađen.");
            response.setOpisAranzmana("Nije moguće pronaći aranžman povezan sa ovom žalbom.");
            response.setOsnovnaCena(0.0);
            response.setBrojNocenja(0);
            response.setDestinacijaNaziv("-");
            response.setDestinacijaDrzava("-");
            response.setNazivSmestaja("-");
            response.setKategorijaSmestaja("-");
            response.setTipPrevoza("-");
            response.setKompanijaPrevoza("-");
        }

        User putnik = null;

        if (zalba.getPutnikId() != null) {
            putnik = userRepository.findById(zalba.getPutnikId()).orElse(null);
        }

        if (putnik != null) {
            response.setPutnikId(putnik.getId());
            response.setImePutnika(putnik.getFirstName());
            response.setPrezimePutnika(putnik.getLastName());
            response.setUsernamePutnika(putnik.getUsername());
            response.setEmailPutnika(putnik.getEmail());
            response.setKontaktPutnika(putnik.getContact());
        } else {
            response.setPutnikId(zalba.getPutnikId());
            response.setImePutnika("-");
            response.setPrezimePutnika("-");
            response.setUsernamePutnika("-");
            response.setEmailPutnika("-");
            response.setKontaktPutnika("-");
        }

        OperativnaDokumentacijaTure dokumentacija = null;

        if (arrangement != null && arrangement.getId() != null) {
            dokumentacija = dokumentacijaRepository
                    .findByArrangementId(arrangement.getId())
                    .orElse(null);

            if (dokumentacija == null) {
                dokumentacija = kreirajDokumentacijuZaAranzman(arrangement);
            }
        }

        if (dokumentacija != null) {
            response.setKontaktSmestaja(dokumentacija.getKontaktSmestaja());
            response.setEmailSmestaja(dokumentacija.getEmailSmestaja());
            response.setOdgovornaOsobaSmestaja(dokumentacija.getOdgovornaOsobaSmestaja());

            response.setKontaktPrevoznika(dokumentacija.getKontaktPrevoznika());
            response.setEmailPrevoznika(dokumentacija.getEmailPrevoznika());
            response.setOdgovornaOsobaPrevoznika(dokumentacija.getOdgovornaOsobaPrevoznika());

            response.setKontaktHitno(dokumentacija.getKontaktHitno());
            response.setPlanPuta(dokumentacija.getPlanPuta());
            response.setNapomeneZaTim(dokumentacija.getNapomeneZaTim());
        }

        return response;
    }

    private OperativnaDokumentacijaTure kreirajDokumentacijuZaAranzman(Arrangement arrangement) {
        String nazivSmestaja = "smeštaja";
        String nazivPrevoznika = "prevoznika";

        if (arrangement.getAccommodation() != null && arrangement.getAccommodation().getName() != null) {
            nazivSmestaja = arrangement.getAccommodation().getName();
        }

        if (arrangement.getTransport() != null && arrangement.getTransport().getCompany() != null) {
            nazivPrevoznika = arrangement.getTransport().getCompany();
        }

        OperativnaDokumentacijaTure dokumentacija = new OperativnaDokumentacijaTure(
                arrangement.getId(),
                "+381 60 100 200",
                "smestaj-" + arrangement.getId() + "@luxtravel.com",
                "Odgovorna osoba za " + nazivSmestaja,
                "+381 60 300 400",
                "prevoz-" + arrangement.getId() + "@luxtravel.com",
                "Odgovorna osoba za " + nazivPrevoznika,
                "+381 60 999 888",
                "Plan puta za aranžman \"" + arrangement.getName() + "\": dolazak na destinaciju, smeštaj putnika, realizacija programa putovanja i povratak.",
                "Tim treba da proveri sve podatke o aranžmanu, kontaktira smeštaj ili prevoznika u zavisnosti od tipa žalbe i evidentira preduzete mere."
        );

        return dokumentacijaRepository.save(dokumentacija);
    }
}