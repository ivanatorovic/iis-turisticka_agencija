package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.Obavestenje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObavestenjeRepository extends JpaRepository<Obavestenje, Long> {

    List<Obavestenje> findByPrimalacUloga(String primalacUloga);

    List<Obavestenje> findByPrimalacUlogaAndPrimalacId(String primalacUloga, Long primalacId);

    List<Obavestenje> findByZalbaId(Long zalbaId);
}