package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.AdditionalActivityExecution;
import com.example.turisticka_agencija.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = """
            SELECT
                COALESCE(SUM(registrations_count), 0) AS totalRegistrations,
                COALESCE(SUM(participants_count), 0) AS totalParticipants,
                ROUND(COALESCE(SUM(revenue), 0)::numeric, 2) AS totalRevenue,
                ROUND(COALESCE(AVG(occupancy_rate), 0)::numeric, 2) AS averageOccupancy
            FROM get_additional_activity_analytics_report(
                :arrangementId,
                :arrangementTermId
            )
            """, nativeQuery = true)
    AdditionalActivityAnalyticsSummaryProjection getSummary(
            @Param("arrangementId") Long arrangementId,
            @Param("arrangementTermId") Long arrangementTermId
    );

    @Query(value = """
            SELECT
                activity_name AS activityName,
                COALESCE(SUM(registrations_count), 0) AS registrationsCount,
                COALESCE(SUM(participants_count), 0) AS participantsCount
            FROM get_additional_activity_analytics_report(
                :arrangementId,
                :arrangementTermId
            )
            GROUP BY activity_name
            ORDER BY COALESCE(SUM(participants_count), 0) DESC
            """, nativeQuery = true)
    List<ActivityPopularityProjection> getPopularity(
            @Param("arrangementId") Long arrangementId,
            @Param("arrangementTermId") Long arrangementTermId
    );

    @Query(value = """
            SELECT
                activity_name AS activityName,
                capacity AS capacity,
                reserved_spots AS reservedSpots,
                ROUND(COALESCE(occupancy_rate, 0)::numeric, 2) AS occupancyRate
            FROM get_additional_activity_analytics_report(
                :arrangementId,
                :arrangementTermId
            )
            ORDER BY occupancy_rate DESC
            """, nativeQuery = true)
    List<ActivityOccupancyProjection> getOccupancy(
            @Param("arrangementId") Long arrangementId,
            @Param("arrangementTermId") Long arrangementTermId
    );

    @Query(value = """
            SELECT
                activity_name AS activityName,
                ROUND(COALESCE(SUM(revenue), 0)::numeric, 2) AS revenue
            FROM get_additional_activity_analytics_report(
                :arrangementId,
                :arrangementTermId
            )
            GROUP BY activity_name
            ORDER BY COALESCE(SUM(revenue), 0) DESC
            """, nativeQuery = true)
    List<ActivityRevenueProjection> getRevenue(
            @Param("arrangementId") Long arrangementId,
            @Param("arrangementTermId") Long arrangementTermId
    );

    @Query(value = """
            SELECT
                activity_name AS activityName,
                ROUND(COALESCE(cancel_rate, 0)::numeric, 2) AS cancelRate
            FROM get_additional_activity_analytics_report(
                :arrangementId,
                :arrangementTermId
            )
            ORDER BY cancel_rate DESC
            """, nativeQuery = true)
    List<ActivityCancellationProjection> getCancellations(
            @Param("arrangementId") Long arrangementId,
            @Param("arrangementTermId") Long arrangementTermId
    );

    @Query(value = """
            SELECT
                guide_name AS guideName,
                COUNT(*) AS executionsCount,
                COALESCE(SUM(participants_count), 0) AS participantsCount
            FROM get_additional_activity_analytics_report(
                :arrangementId,
                :arrangementTermId
            )
            GROUP BY guide_name
            ORDER BY COALESCE(SUM(participants_count), 0) DESC
            """, nativeQuery = true)
    List<GuideWorkloadProjection> getGuideWorkload(
            @Param("arrangementId") Long arrangementId,
            @Param("arrangementTermId") Long arrangementTermId
    );
}