package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.AdditionalActivityExecution;
import com.example.turisticka_agencija.projection.AdditionalActivityAnalyticsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdditionalActivityAnalyticsRepository
        extends JpaRepository<AdditionalActivityExecution, Long> {

    @Query(value = """
            SELECT *
            FROM get_additional_activity_analytics_report(
                :arrangementId,
                :arrangementTermId
            )
            """, nativeQuery = true)
    List<AdditionalActivityAnalyticsProjection> getAnalyticsReport(

            @Param("arrangementId") Long arrangementId,

            @Param("arrangementTermId") Long arrangementTermId
    );

}