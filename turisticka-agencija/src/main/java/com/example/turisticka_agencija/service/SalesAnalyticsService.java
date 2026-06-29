package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SalesAnalyticsService {

    private final ReservationRepository reservationRepository;

    public SalesAnalyticsService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public YearlySalesSummaryDto getYearlySummary(int year) {
        YearlySalesSummaryProjection data = reservationRepository.getYearlySummaryData(year);

        long totalReservations = safeLong(data.getTotalReservations());
        long confirmedReservations = safeLong(data.getConfirmedReservations());
        long cancelledReservations = safeLong(data.getCancelledReservations());
        double totalRevenue = safeDouble(data.getTotalRevenue());

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
        return reservationRepository.getRevenueByYears();
    }

    public List<MonthlyArrangementSalesDto> getMonthlyArrangementSales(Long arrangementId, int year) {
        List<MonthlyArrangementSalesProjection> rows =
                reservationRepository.getMonthlyArrangementSalesData(arrangementId, year);

        Map<Integer, MonthlyArrangementSalesProjection> byMonth = new HashMap<>();

        for (MonthlyArrangementSalesProjection row : rows) {
            byMonth.put(row.getMonth(), row);
        }

        String[] months = {
                "Januar", "Februar", "Mart", "April",
                "Maj", "Jun", "Jul", "Avgust",
                "Septembar", "Oktobar", "Novembar", "Decembar"
        };

        List<MonthlyArrangementSalesDto> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            MonthlyArrangementSalesProjection row = byMonth.get(month);

            long reservationCount = row != null ? safeLong(row.getReservationCount()) : 0;
            double revenue = row != null ? safeDouble(row.getRevenue()) : 0;

            result.add(new MonthlyArrangementSalesDto(
                    month,
                    months[month - 1],
                    reservationCount,
                    revenue
            ));
        }

        return result;
    }

    public List<PopularDestinationDto> getPopularDestinations(int year) {
        return reservationRepository.getPopularDestinations(year);
    }

    public List<PopularArrangementDto> getPopularArrangements(int year) {
        return reservationRepository.getPopularArrangements(year);
    }

    private long safeLong(Long value) {
        return value == null ? 0 : value;
    }

    private double safeDouble(Double value) {
        return value == null ? 0 : value;
    }
}