package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ArrangementRepository arrangementRepository;
    private final TermRepository termRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 UserRepository userRepository,
                                 ArrangementRepository arrangementRepository,
                                 TermRepository termRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.arrangementRepository = arrangementRepository;
        this.termRepository = termRepository;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @PostMapping
    public Reservation createReservation(@RequestParam Long userId,
                                         @RequestParam Long arrangementId,
                                         @RequestParam Long termId,
                                         @RequestParam int passengers) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Arrangement arrangement = arrangementRepository.findById(arrangementId)
                .orElseThrow(() -> new RuntimeException("Arrangement not found"));

        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new RuntimeException("Term not found"));

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setArrangement(arrangement);
        reservation.setTerm(term);

        reservation.setNumberOfPassengers(passengers);

        reservation.setTotalPrice(
                arrangement.getBasePrice() * passengers
        );

        return reservationRepository.save(reservation);
    }
}