package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    List<Workflow> findByAdminUsername(String username);
    List<Workflow> findByManagerUsername(String username);
}