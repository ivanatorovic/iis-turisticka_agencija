package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.AdditionalActivityRegistration;
import com.example.turisticka_agencija.model.AdditionalActivityRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdditionalActivityRegistrationRepository
        extends JpaRepository<AdditionalActivityRegistration, Long> {

    List<AdditionalActivityRegistration> findByUserIdOrderByRegistrationDateDesc(Long userId);

    List<AdditionalActivityRegistration> findByAdditionalActivityExecutionId(Long executionId);

    Optional<AdditionalActivityRegistration>
    findByUserIdAndAdditionalActivityExecutionIdAndStatus(
            Long userId,
            Long executionId,
            AdditionalActivityRegistrationStatus status
    );

    boolean existsByUserIdAndAdditionalActivityExecutionIdAndStatus(
            Long userId,
            Long executionId,
            AdditionalActivityRegistrationStatus status
    );

    void deleteByAdditionalActivityExecutionId(Long executionId);
}