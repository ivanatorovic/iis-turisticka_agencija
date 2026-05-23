package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.AdditionalActivityPriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdditionalActivityPriceListRepository
        extends JpaRepository<AdditionalActivityPriceList, Long> {

    Optional<AdditionalActivityPriceList> findFirstByAdditionalActivityExecutionIdOrderByIdDesc(
            Long additionalActivityExecutionId

    );

    void deleteByAdditionalActivityExecutionId(Long additionalActivityExecutionId);
}