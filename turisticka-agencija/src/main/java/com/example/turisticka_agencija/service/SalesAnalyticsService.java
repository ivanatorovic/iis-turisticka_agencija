package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.model.Reservation;
import com.example.turisticka_agencija.model.ReservationStatus;
import com.example.turisticka_agencija.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesAnalyticsService {

    private final ReservationRepository reservationRepository;

    public SalesAnalyticsService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public YearlySalesSummaryDto getYearlySummary(int year) {
        List<Reservation> reservationsForYear = reservationRepository.findAll()
                .stream()
                .filter(reservation -> getTravelYear(reservation) == year)
                .toList();

        long totalReservations = reservationsForYear.size();

        long confirmedReservations = reservationsForYear.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .count();

        long cancelledReservations = reservationsForYear.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CANCELLED)
                .count();

        double totalRevenue = reservationsForYear.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .mapToDouble(Reservation::getTotalPrice)
                .sum();

        double averageReservationValue = confirmedReservations > 0
                ? totalRevenue / confirmedReservations
                : 0;

        return new YearlySalesSummaryDto(
                year,
                totalReservations,
                confirmedReservations,
                cancelledReservations,
                totalRevenue,
                averageReservationValue
        );
    }

    public List<YearRevenueDto> getRevenueByYears() {
        return reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .collect(Collectors.groupingBy(this::getTravelYear))
                .entrySet()
                .stream()
                .map(entry -> new YearRevenueDto(
                        entry.getKey(),
                        entry.getValue().size(),
                        entry.getValue()
                                .stream()
                                .mapToDouble(Reservation::getTotalPrice)
                                .sum()
                ))
                .sorted(Comparator.comparingInt(YearRevenueDto::getYear))
                .toList();
    }

    public List<MonthlyArrangementSalesDto> getMonthlyArrangementSales(Long arrangementId, int year) {
        List<Reservation> reservations = reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .filter(reservation -> reservation.getArrangement().getId().equals(arrangementId))
                .filter(reservation -> getTravelYear(reservation) == year)
                .toList();

        List<MonthlyArrangementSalesDto> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            int currentMonth = month;

            List<Reservation> reservationsForMonth = reservations.stream()
                    .filter(reservation -> getTravelMonth(reservation) == currentMonth)
                    .toList();

            long count = reservationsForMonth.size();

            double revenue = reservationsForMonth.stream()
                    .mapToDouble(Reservation::getTotalPrice)
                    .sum();

            String monthName = Month.of(month)
                    .getDisplayName(TextStyle.FULL, new Locale("sr", "RS"));

            result.add(new MonthlyArrangementSalesDto(
                    month,
                    monthName,
                    count,
                    revenue
            ));
        }

        return result;
    }

    public List<PopularDestinationDto> getPopularDestinations(int year) {
        return reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .filter(reservation -> getTravelYear(reservation) == year)
                .collect(Collectors.groupingBy(reservation ->
                        reservation.getArrangement().getDestination().getName()
                                + "|" +
                                reservation.getArrangement().getDestination().getCountry()
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    String[] parts = entry.getKey().split("\\|");

                    long count = entry.getValue().size();

                    double revenue = entry.getValue()
                            .stream()
                            .mapToDouble(Reservation::getTotalPrice)
                            .sum();

                    return new PopularDestinationDto(
                            parts[0],
                            parts[1],
                            count,
                            revenue
                    );
                })
                .sorted(Comparator.comparingLong(PopularDestinationDto::getReservationCount).reversed())
                .toList();
    }

    public List<PopularArrangementDto> getPopularArrangements(int year) {
        return reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .filter(reservation -> getTravelYear(reservation) == year)
                .collect(Collectors.groupingBy(reservation -> reservation.getArrangement().getId()))
                .entrySet()
                .stream()
                .map(entry -> {
                    Reservation first = entry.getValue().get(0);

                    long count = entry.getValue().size();

                    double revenue = entry.getValue()
                            .stream()
                            .mapToDouble(Reservation::getTotalPrice)
                            .sum();

                    return new PopularArrangementDto(
                            first.getArrangement().getId(),
                            first.getArrangement().getName(),
                            count,
                            revenue
                    );
                })
                .sorted(Comparator.comparingLong(PopularArrangementDto::getReservationCount).reversed())
                .toList();
    }

    private int getTravelYear(Reservation reservation) {
        return reservation.getArrangementTerm()
                .getTerm()
                .getStartDate()
                .getYear();
    }

    private int getTravelMonth(Reservation reservation) {
        return reservation.getArrangementTerm()
                .getTerm()
                .getStartDate()
                .getMonthValue();
    }
}