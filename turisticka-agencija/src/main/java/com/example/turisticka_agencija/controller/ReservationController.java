package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.AvailabilityResponse;
import com.example.turisticka_agencija.dto.CreateReservationRequest;
import com.example.turisticka_agencija.model.Reservation;
import com.example.turisticka_agencija.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/my")
    public List<Reservation> getMyReservations(@RequestParam Long userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @GetMapping("/check-availability")
    public AvailabilityResponse checkAvailability(@RequestParam Long arrangementId,
                                                  @RequestParam Long arrangementTermId,
                                                  @RequestParam int passengers) {
        return reservationService.checkAvailability(
                arrangementId,
                arrangementTermId,
                passengers
        );
    }

    @PostMapping
    public Reservation createReservation(@RequestBody CreateReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @PutMapping("/{id}/cancel")
    public Reservation cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }
}