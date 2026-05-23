package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.KorakZivotnogCiklusa;
import com.example.turisticka_agencija.model.StatusZalbe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KorakZivotnogCiklusaRepository extends JpaRepository<KorakZivotnogCiklusa, Long> {

    List<KorakZivotnogCiklusa> findByAktivanTrueOrderByRedosledAsc();

    Optional<KorakZivotnogCiklusa> findByStatus(StatusZalbe status);
}