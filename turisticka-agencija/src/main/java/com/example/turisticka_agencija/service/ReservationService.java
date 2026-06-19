package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.AlternativeTermDto;
import com.example.turisticka_agencija.dto.AvailabilityResponse;
import com.example.turisticka_agencija.dto.CreateReservationRequest;
import com.example.turisticka_agencija.dto.ReservationPassengerRequest;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ArrangementRepository arrangementRepository;
    private final ArrangementTermRepository arrangementTermRepository;
    private final DynamicPricingService dynamicPricingService;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              ArrangementRepository arrangementRepository,
                              ArrangementTermRepository arrangementTermRepository,
                              DynamicPricingService dynamicPricingService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.arrangementRepository = arrangementRepository;
        this.arrangementTermRepository = arrangementTermRepository;
        this.dynamicPricingService = dynamicPricingService;
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

        if (request.getPassengers() == null
                || request.getPassengers().size() != request.getNumberOfPassengers()) {
            throw new RuntimeException("Broj putnika nije ispravan");
        }

        double basePricePerPerson = arrangement.getBasePrice();

        double dynamicPricePerPerson =
                dynamicPricingService.calculatePrice(arrangement, arrangementTerm);

        Reservation reservation = new Reservation();

        double arrangementTotalPrice = 0;

        List<ReservationPassenger> reservationPassengers = new ArrayList<>();

        for (ReservationPassengerRequest passengerRequest : request.getPassengers()) {
            ReservationPassenger passenger = new ReservationPassenger();

            passenger.setFirstName(passengerRequest.getFirstName());
            passenger.setLastName(passengerRequest.getLastName());
            passenger.setAge(passengerRequest.getAge());

            double passengerPrice;
            String discountDescription;

            if (passengerRequest.getAge() < 5) {
                passengerPrice = 0;
                discountDescription = "Dete do 5 godina - gratis";
            } else if (passengerRequest.getAge() <= 12) {
                passengerPrice = dynamicPricePerPerson * 0.5;
                discountDescription = "Dečiji popust 50%";
            } else {
                passengerPrice = dynamicPricePerPerson;
                discountDescription = "Puna cena";
            }

            passenger.setPrice(passengerPrice);
            passenger.setDiscountDescription(discountDescription);
            passenger.setReservation(reservation);

            arrangementTotalPrice += passengerPrice;
            reservationPassengers.add(passenger);
        }

        double insurancePrice = 0;

        if (request.isInsuranceSelected()) {
            insurancePrice = 30 * request.getNumberOfPassengers();
        }

        double totalPrice = arrangementTotalPrice + insurancePrice;

        if (request.getExpectedTotalPrice() != null
                && Math.abs(totalPrice - request.getExpectedTotalPrice()) > 0.01) {
            throw new RuntimeException(
                    "Cena se promenila. Nova cena je "
                            + totalPrice
                            + " €. Molimo proverite obračun i pokušajte ponovo."
            );
        }

        reservation.setUser(user);
        reservation.setArrangement(arrangement);
        reservation.setArrangementTerm(arrangementTerm);
        reservation.setPassengers(reservationPassengers);

        reservation.setNumberOfPassengers(request.getNumberOfPassengers());

        reservation.setPassengerFirstName(request.getPassengerFirstName());
        reservation.setPassengerLastName(request.getPassengerLastName());
        reservation.setPassengerEmail(request.getPassengerEmail());

        reservation.setBasePricePerPerson(basePricePerPerson);
        reservation.setDynamicPricePerPerson(dynamicPricePerPerson);

        reservation.setInsuranceSelected(request.isInsuranceSelected());
        reservation.setInsurancePrice(insurancePrice);

        reservation.setArrangementTotalPrice(arrangementTotalPrice);
        reservation.setTotalPrice(totalPrice);

        reservation.setStatus(ReservationStatus.CONFIRMED);

        PaymentType paymentType = request.getPaymentType() != null
                ? request.getPaymentType()
                : PaymentType.ONE_TIME;

        reservation.setPaymentType(paymentType);

        if (paymentType == PaymentType.INSTALLMENTS) {
            if (request.getNumberOfInstallments() == null || request.getNumberOfInstallments() < 2) {
                throw new RuntimeException("Number of installments must be at least 2");
            }

            reservation.setNumberOfInstallments(request.getNumberOfInstallments());
            reservation.setInstallmentAmount(totalPrice / request.getNumberOfInstallments());
        } else {
            reservation.setNumberOfInstallments(1);
            reservation.setInstallmentAmount(totalPrice);
        }

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