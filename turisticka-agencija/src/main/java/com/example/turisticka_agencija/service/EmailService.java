package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.model.Reservation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReservationConfirmation(Reservation reservation) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(reservation.getPassengerEmail());
        message.setSubject("Potvrda rezervacije aranžmana");

        message.setText(
                "Poštovani/a " + reservation.getPassengerFirstName() + ",\n\n" +
                        "Vaša rezervacija je uspešno potvrđena.\n\n" +
                        "Aranžman: " + reservation.getArrangement().getName() + "\n" +
                        "Broj putnika: " + reservation.getNumberOfPassengers() + "\n" +
                        "Ukupna cena: " + reservation.getTotalPrice() + " €\n" +
                        "Status: " + reservation.getStatus() + "\n\n" +
                        "Hvala što koristite LUXTRAVEL."
        );

        mailSender.send(message);
    }
}