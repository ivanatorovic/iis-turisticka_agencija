package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.AlternativeTermDto;
import com.example.turisticka_agencija.dto.AvailabilityResponse;
import com.example.turisticka_agencija.dto.CreateReservationRequest;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ArrangementRepository arrangementRepository;
    private final ArrangementTermRepository arrangementTermRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              ArrangementRepository arrangementRepository,
                              ArrangementTermRepository arrangementTermRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.arrangementRepository = arrangementRepository;
        this.arrangementTermRepository = arrangementTermRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public AvailabilityResponse checkAvailability(Long arrangementId,
                                                  Long arrangementTermId,
                                                  int passengers) {

        ArrangementTerm selectedArrangementTerm = arrangementTermRepository.findById(arrangementTermId)
                .orElseThrow(() -> new RuntimeException("Arrangement term not found"));

        if (!selectedArrangementTerm.getArrangement().getId().equals(arrangementId)) {
            throw new RuntimeException("Selected term does not belong to this arrangement");
        }

        int availableSpots = selectedArrangementTerm.getAvailableSpots();

        if (availableSpots >= passengers) {
            return new AvailabilityResponse(true, availableSpots, List.of());
        }

        List<AlternativeTermDto> alternatives = arrangementTermRepository.findByArrangementId(arrangementId)
                .stream()
                .filter(at -> !at.getId().equals(arrangementTermId))
                .filter(at -> at.getAvailableSpots() >= passengers)
                .map(at -> new AlternativeTermDto(
                        at.getId(),
                        at.getTerm().getStartDate(),
                        at.getTerm().getEndDate(),
                        at.getAvailableSpots()
                ))
                .toList();

        return new AvailabilityResponse(false, availableSpots, alternatives);
    }

    public Reservation createReservation(CreateReservationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Arrangement arrangement = arrangementRepository.findById(request.getArrangementId())
                .orElseThrow(() -> new RuntimeException("Arrangement not found"));

        ArrangementTerm arrangementTerm = arrangementTermRepository.findById(request.getArrangementTermId())
                .orElseThrow(() -> new RuntimeException("Arrangement term not found"));

        if (!arrangementTerm.getArrangement().getId().equals(arrangement.getId())) {
            throw new RuntimeException("Selected term does not belong to this arrangement");
        }

        if (arrangementTerm.getAvailableSpots() < request.getNumberOfPassengers()) {
            throw new RuntimeException("Not enough available spots");
        }

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setArrangement(arrangement);
        reservation.setArrangementTerm(arrangementTerm);
        reservation.setNumberOfPassengers(request.getNumberOfPassengers());
        reservation.setTotalPrice(arrangement.getBasePrice() * request.getNumberOfPassengers());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        arrangementTerm.setReservedSpots(
                arrangementTerm.getReservedSpots() + request.getNumberOfPassengers()
        );

        arrangementTermRepository.save(arrangementTerm);

        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Reservation is already cancelled");
        }

        ArrangementTerm arrangementTerm = reservation.getArrangementTerm();

        arrangementTerm.setReservedSpots(
                arrangementTerm.getReservedSpots() - reservation.getNumberOfPassengers()
        );

        if (arrangementTerm.getReservedSpots() < 0) {
            arrangementTerm.setReservedSpots(0);
        }

        arrangementTermRepository.save(arrangementTerm);

        reservation.setStatus(ReservationStatus.CANCELLED);

        return reservationRepository.save(reservation);
    }
}