package com.example.turisticka_agencija.config;

import com.example.turisticka_agencija.model.OperativnaDokumentacijaTure;
import com.example.turisticka_agencija.repository.OperativnaDokumentacijaTureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OperativnaDokumentacijaTureDataInitializer implements CommandLineRunner {

    private final OperativnaDokumentacijaTureRepository repository;

    public OperativnaDokumentacijaTureDataInitializer(OperativnaDokumentacijaTureRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.deleteAll();

        kreirajAkoNePostoji(
                1L,
                "+30 210 555 1234",
                "hotel.krf@example.com",
                "Maria Papadopoulos",
                "+381 60 777 888",
                "prevoz.krf@example.com",
                "Nikola Petrović",
                "+381 60 111 222",
                "1. dan: Polazak i dolazak na destinaciju. 2. dan: Smeštaj i slobodno vreme. 3. dan: Obilazak lokalnih znamenitosti. Poslednji dan: Povratak.",
                "Za probleme sa smeštajem prvo kontaktirati odgovornu osobu hotela. Za probleme sa prevozom kontaktirati prevoznika. Ako nema odgovora, obavestiti hitni kontakt agencije i predložiti alternativno rešenje."
        );

        kreirajAkoNePostoji(
                2L,
                "+20 65 333 444",
                "hotel.hurgada@example.com",
                "Ahmed Hassan",
                "+381 60 999 111",
                "prevoz.hurgada@example.com",
                "Milan Jovanović",
                "+381 60 333 444",
                "1. dan: Let i transfer do hotela. 2. dan: Check-in i informativni sastanak. 3-6. dan: Boravak i fakultativni izleti. Poslednji dan: Transfer do aerodroma i povratak.",
                "Za probleme sa prevozom prvo kontaktirati prevoznika. Ako prevoznik ne reaguje, organizovati alternativni transfer i obavestiti putnika kroz sistem."
        );

        kreirajAkoNePostoji(
                3L,
                "+39 06 555 123",
                "hotel.rim@example.com",
                "Luca Romano",
                "+381 60 222 333",
                "prevoz.rim@example.com",
                "Petar Ilić",
                "+381 60 555 666",
                "1. dan: Dolazak u Rim i smeštaj. 2. dan: Obilazak Koloseuma. 3. dan: Obilazak Vatikana. Poslednji dan: Povratak.",
                "Za sve probleme u toku putovanja proveriti podatke o smeštaju, prevozu i dodatnim aktivnostima. Po potrebi kontaktirati hotel, prevoznika ili hitni kontakt agencije."
        );
    }

    private void kreirajAkoNePostoji(
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
        if (repository.existsByArrangementId(arrangementId)) {
            return;
        }

        OperativnaDokumentacijaTure dokumentacija = new OperativnaDokumentacijaTure(
                arrangementId,
                kontaktSmestaja,
                emailSmestaja,
                odgovornaOsobaSmestaja,
                kontaktPrevoznika,
                emailPrevoznika,
                odgovornaOsobaPrevoznika,
                kontaktHitno,
                planPuta,
                napomeneZaTim
        );

        repository.save(dokumentacija);
    }
}