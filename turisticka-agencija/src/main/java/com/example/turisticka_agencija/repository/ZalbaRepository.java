package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.StatusZalbe;
import com.example.turisticka_agencija.model.TimZalbe;
import com.example.turisticka_agencija.model.TipZalbe;
import com.example.turisticka_agencija.model.Zalba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZalbaRepository extends JpaRepository<Zalba, Long> {

    List<Zalba> findByPutnikId(Long putnikId);

    List<Zalba> findByStatus(StatusZalbe status);

    List<Zalba> findByDodeljeniTim(TimZalbe dodeljeniTim);

    List<Zalba> findByTipZalbe(TipZalbe tipZalbe);

    List<Zalba> findByHitnaTrue();

    long countByStatus(StatusZalbe status);

    long countByDodeljeniTim(TimZalbe timZalbe);

    long countByTipZalbe(TipZalbe tipZalbe);
}