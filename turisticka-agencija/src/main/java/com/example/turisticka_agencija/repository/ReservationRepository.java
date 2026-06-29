package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    List<Reservation> findByUserId(Long userId);

    @Query("""
        SELECT 
            COUNT(r) AS totalReservations,
            SUM(CASE WHEN r.status = com.example.turisticka_agencija.model.ReservationStatus.CONFIRMED THEN 1 ELSE 0 END) AS confirmedReservations,
            SUM(CASE WHEN r.status = com.example.turisticka_agencija.model.ReservationStatus.CANCELLED THEN 1 ELSE 0 END) AS cancelledReservations,
            COALESCE(SUM(CASE WHEN r.status = com.example.turisticka_agencija.model.ReservationStatus.CONFIRMED THEN r.totalPrice ELSE 0 END), 0) AS totalRevenue
        FROM Reservation r
        JOIN r.arrangementTerm at
        JOIN at.term t
        WHERE YEAR(t.startDate) = :year
    """)
    YearlySalesSummaryProjection getYearlySummaryData(@Param("year") int year);

    @Query("""
        SELECT new com.example.turisticka_agencija.dto.YearRevenueDto(
            YEAR(t.startDate),
            COUNT(r),
            COALESCE(SUM(r.totalPrice), 0)
        )
        FROM Reservation r
        JOIN r.arrangementTerm at
        JOIN at.term t
        WHERE r.status = com.example.turisticka_agencija.model.ReservationStatus.CONFIRMED
        GROUP BY YEAR(t.startDate)
        ORDER BY YEAR(t.startDate)
    """)
    List<YearRevenueDto> getRevenueByYears();

    @Query("""
        SELECT 
            MONTH(t.startDate) AS month,
            COUNT(r) AS reservationCount,
            COALESCE(SUM(r.totalPrice), 0) AS revenue
        FROM Reservation r
        JOIN r.arrangement a
        JOIN r.arrangementTerm at
        JOIN at.term t
        WHERE r.status = com.example.turisticka_agencija.model.ReservationStatus.CONFIRMED
          AND a.id = :arrangementId
          AND YEAR(t.startDate) = :year
        GROUP BY MONTH(t.startDate)
        ORDER BY MONTH(t.startDate)
    """)
    List<MonthlyArrangementSalesProjection> getMonthlyArrangementSalesData(
            @Param("arrangementId") Long arrangementId,
            @Param("year") int year
    );

    @Query("""
        SELECT new com.example.turisticka_agencija.dto.PopularDestinationDto(
            d.name,
            d.country,
            COUNT(r),
            COALESCE(SUM(r.totalPrice), 0)
        )
        FROM Reservation r
        JOIN r.arrangement a
        JOIN a.destination d
        JOIN r.arrangementTerm at
        JOIN at.term t
        WHERE r.status = com.example.turisticka_agencija.model.ReservationStatus.CONFIRMED
          AND YEAR(t.startDate) = :year
        GROUP BY d.name, d.country
        ORDER BY COUNT(r) DESC
    """)
    List<PopularDestinationDto> getPopularDestinations(@Param("year") int year);

    @Query("""
        SELECT new com.example.turisticka_agencija.dto.PopularArrangementDto(
            a.id,
            a.name,
            COUNT(r),
            COALESCE(SUM(r.totalPrice), 0)
        )
        FROM Reservation r
        JOIN r.arrangement a
        JOIN r.arrangementTerm at
        JOIN at.term t
        WHERE r.status = com.example.turisticka_agencija.model.ReservationStatus.CONFIRMED
          AND YEAR(t.startDate) = :year
        GROUP BY a.id, a.name
        ORDER BY COUNT(r) DESC
    """)
    List<PopularArrangementDto> getPopularArrangements(@Param("year") int year);
}