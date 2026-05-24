package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.AdditionalActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdditionalActivityRepository extends JpaRepository<AdditionalActivity, Long> {
    List<AdditionalActivity> findAllByOrderByIdDesc();
}