package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.OperativnaDokumentacijaTure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperativnaDokumentacijaTureRepository extends JpaRepository<OperativnaDokumentacijaTure, Long> {

    Optional<OperativnaDokumentacijaTure> findByArrangementId(Long arrangementId);

    boolean existsByArrangementId(Long arrangementId);
}