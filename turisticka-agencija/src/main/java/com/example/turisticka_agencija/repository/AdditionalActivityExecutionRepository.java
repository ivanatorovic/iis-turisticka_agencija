package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.AdditionalActivityExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdditionalActivityExecutionRepository
        extends JpaRepository<AdditionalActivityExecution, Long> {

    List<AdditionalActivityExecution> findByArrangementTermId(Long arrangementTermId);

    List<AdditionalActivityExecution> findByGuideId(Long guideId);

    List<AdditionalActivityExecution> findByAdditionalActivityId(Long additionalActivityId);

    @Query("""
        SELECT e
        FROM AdditionalActivityExecution e
        JOIN e.activityTerm at
        JOIN e.additionalActivity aa
        JOIN AdditionalActivityPriceList pl ON pl.additionalActivityExecution = e
        WHERE e.arrangementTerm.id = :arrangementTermId
          AND pl.id = (
              SELECT MAX(pl2.id)
              FROM AdditionalActivityPriceList pl2
              WHERE pl2.additionalActivityExecution = e
          )
          AND (:dateFrom IS NULL OR at.date >= :dateFrom)
          AND (:dateTo IS NULL OR at.date <= :dateTo)
          AND (:minPrice IS NULL OR pl.price >= :minPrice)
          AND (:maxPrice IS NULL OR pl.price <= :maxPrice)
          AND (:minDuration IS NULL OR e.durationMinutes >= :minDuration)
          AND (:maxDuration IS NULL OR e.durationMinutes <= :maxDuration)
          AND (:minAvailableSpots IS NULL OR (e.capacity - e.reservedSpots) >= :minAvailableSpots)
          AND (:onlyAvailable IS NULL OR :onlyAvailable = false OR (e.capacity - e.reservedSpots) > 0)
        ORDER BY at.date ASC, at.startTime ASC
    """)
    List<AdditionalActivityExecution> findFilteredByArrangementTerm(
            @Param("arrangementTermId") Long arrangementTermId,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration,
            @Param("minAvailableSpots") Integer minAvailableSpots,
            @Param("onlyAvailable") Boolean onlyAvailable
    );
}