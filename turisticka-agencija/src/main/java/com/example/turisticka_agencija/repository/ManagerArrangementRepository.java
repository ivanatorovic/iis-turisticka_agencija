package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.ManagerArrangement;
import com.example.turisticka_agencija.model.ManagerArrangementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerArrangementRepository extends JpaRepository<ManagerArrangement, Long> {

    List<ManagerArrangement> findByManagerId(Long managerId);

    List<ManagerArrangement> findByWorkflowId(Long workflowId);

    List<ManagerArrangement> findByStatus(ManagerArrangementStatus status);
}