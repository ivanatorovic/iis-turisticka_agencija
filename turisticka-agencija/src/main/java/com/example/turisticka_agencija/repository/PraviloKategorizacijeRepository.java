package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.PraviloKategorizacije;
import com.example.turisticka_agencija.model.TipZalbe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PraviloKategorizacijeRepository extends JpaRepository<PraviloKategorizacije, Long> {

    Optional<PraviloKategorizacije> findByTipZalbe(TipZalbe tipZalbe);
}